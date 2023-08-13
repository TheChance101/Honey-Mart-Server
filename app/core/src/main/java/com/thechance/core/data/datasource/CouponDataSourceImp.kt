package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.coupon.CouponTable
import com.thechance.core.data.datasource.database.tables.coupon.CouponUserTable
import com.thechance.core.data.datasource.database.tables.product.GalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductGalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.data.datasource.mapper.toProduct
import com.thechance.core.data.datasource.mapper.toUserCoupon
import com.thechance.core.data.repository.dataSource.CouponDataSource
import com.thechance.core.entity.Image
import com.thechance.core.entity.Product
import com.thechance.core.entity.coupon.MarketCoupon
import com.thechance.core.entity.coupon.UserCoupon
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.*
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

    override suspend fun getCouponsForUser(userId: Long): List<UserCoupon> = dbQuery {
        CouponTable.selectAll().map { resultRow ->
            val isClipped = CouponUserTable.select {
                (CouponUserTable.userId eq userId) and (CouponUserTable.couponId eq resultRow[CouponTable.id].value)
            }.singleOrNull() != null
            val product = getProduct(resultRow[CouponTable.productId].value)
            resultRow.toUserCoupon(product, isClipped)
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

    override suspend fun getCouponsForMarket(marketId: Long): List<MarketCoupon> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCoupon(couponId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun clipCoupon(couponId: Long): Boolean {
        TODO("Not yet implemented")
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