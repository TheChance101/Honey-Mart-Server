package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.coupon.CouponTable
import com.thechance.core.data.datasource.database.tables.coupon.CouponUserTable
import com.thechance.core.data.datasource.database.tables.product.GalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductGalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.data.datasource.mapper.toCoupon
import com.thechance.core.data.datasource.mapper.toMarketCoupon
import com.thechance.core.data.datasource.mapper.toProduct
import com.thechance.core.data.datasource.mapper.toUserCoupon
import com.thechance.core.data.repository.dataSource.CouponDataSource
import com.thechance.core.entity.Image
import com.thechance.core.entity.Product
import com.thechance.core.entity.coupon.Coupon
import com.thechance.core.entity.coupon.MarketCoupon
import com.thechance.core.entity.coupon.UserCoupon
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime

class CouponDataSourceImp : CouponDataSource {
    override suspend fun addCoupon(
        marketId: Long,
        productId: Long,
        count: Int,
        discountPercentage: Double,
        expirationDate: LocalDateTime
    ): Boolean = dbQuery {
        CouponTable.insert {
            it[this.productId] = productId
            it[this.marketId] = marketId
            it[this.count] = count
            it[this.discountPercentage] = discountPercentage
            it[this.expirationDate] = expirationDate
        }
        true
    }

    /**
     * Retrieve coupons for a specific user based on the provided userId.
     * It excludes clipped coupons and coupons with a count less than or equal to 0,
     * and also excludes coupons with an expiration date in the past.
     */
    override suspend fun getCouponsForUser(userId: Long): List<UserCoupon> = dbQuery {
        val currentDate = LocalDateTime.now()
        val userClippedCouponIds = CouponUserTable
            .select { CouponUserTable.userId eq userId }
            .map { it[CouponUserTable.couponId].value }

        CouponTable
            .select {
                (CouponTable.count greater 0) and
                        not(CouponTable.id inList userClippedCouponIds) and
                        (CouponTable.expirationDate greater currentDate)
            }
            .map { resultRow ->
                val product = getProduct(resultRow[CouponTable.productId].value)
                resultRow.toUserCoupon(product, false)
            }
    }

    override suspend fun getAllValidCoupons(): List<Coupon> = dbQuery {
        val currentDate = LocalDateTime.now()
        CouponTable
            .select { (CouponTable.count greater 0) and (CouponTable.expirationDate greater currentDate) }
            .map { resultRow ->
                val product = getProduct(resultRow[CouponTable.productId].value)
                resultRow.toCoupon(product)
            }
    }

    override suspend fun getClippedCouponsForUser(userId: Long): List<UserCoupon> = dbQuery {
        (CouponUserTable innerJoin CouponTable).select {
            (CouponUserTable.userId eq userId) and
                    not(CouponUserTable.isUsed)
        }.map { resultRow ->
            val product = getProduct(resultRow[CouponTable.productId].value)
            resultRow.toUserCoupon(product, true)
        }
    }

    override suspend fun getCouponsForMarket(marketId: Long): List<MarketCoupon> = dbQuery {
        val currentDate = LocalDateTime.now()
        val marketProducts = ProductTable
            .select { ProductTable.marketId eq marketId }
            .map { it[ProductTable.id].value }
        CouponTable
            .select {
                (CouponTable.count greater 0) and
                        (CouponTable.expirationDate greater currentDate) and
                        (CouponTable.productId inList marketProducts)
            }
            .map { resultRow ->
                val product = getProduct(resultRow[CouponTable.productId].value)
                resultRow.toMarketCoupon(product)
            }
    }

    override suspend fun deleteCoupon(couponId: Long): Boolean = dbQuery {
        val rowsAffected = CouponTable.deleteWhere { CouponTable.id eq couponId }
        rowsAffected > 0
    }

    override suspend fun clipCoupon(couponId: Long, userId: Long): Boolean = dbQuery {
        CouponUserTable.insert {
            it[CouponUserTable.couponId] = couponId
            it[CouponUserTable.userId] = userId
        }
        true
    }

    override suspend fun isCouponClipped(couponId: Long, userId: Long): Boolean = dbQuery {
        CouponUserTable
            .select { (CouponUserTable.couponId eq couponId) and (CouponUserTable.userId eq userId) }
            .count() > 0
    }

    override suspend fun isValidCoupon(couponId: Long): Boolean = dbQuery {
        val currentDate = LocalDateTime.now()
        CouponTable
            .select {
                (CouponTable.id eq couponId) and (CouponTable.count greater 0) and
                        (CouponTable.expirationDate greater currentDate)
            }
            .count() > 0
    }

    private fun getProduct(productId: Long): Product {
        return ProductTable.select { ProductTable.id eq productId }.map { productRow ->
            val images = getProductImages(productRow[ProductTable.id].value)
            productRow.toProduct(images = images)
        }.single()
    }

    private fun getProductImages(productId: Long): List<Image> {
        return (GalleryTable innerJoin ProductGalleryTable)
            .select { ProductGalleryTable.productId eq productId }
            .map { imageRow ->
                Image(
                    id = imageRow[GalleryTable.id].value,
                    imageUrl = imageRow[GalleryTable.imageUrl],
                )
            }
    }
}