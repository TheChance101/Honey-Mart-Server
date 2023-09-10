package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.product.Product
import com.thechance.core.entity.coupon.Coupon
import com.thechance.core.entity.coupon.MarketCoupon
import com.thechance.core.entity.coupon.UserCoupon
import java.time.LocalDateTime

interface CouponDataSource {
    suspend fun addCoupon(
        marketId: Long,
        productId: Long,
        count: Int,
        discountPercentage: Double,
        expirationDate: LocalDateTime
    ): Boolean

    suspend fun getCouponsForUser(userId: Long): List<UserCoupon>
    suspend fun getClippedCouponsForUser(userId: Long): List<UserCoupon>
    suspend fun getCouponsForMarket(marketId: Long): List<MarketCoupon>
    suspend fun deleteCoupon(couponId: Long): Boolean
    suspend fun clipCoupon(couponId: Long, userId: Long): Boolean
    suspend fun useCoupon(couponId: Long, userId: Long): Boolean
    suspend fun isCouponClipped(couponId: Long, userId: Long): Boolean
    suspend fun isValidCoupon(couponId: Long): Boolean
    suspend fun getAllValidCoupons(): List<Coupon>
    suspend fun getProductsWithoutValidCoupons(marketId: Long): List<Product>
    suspend fun searchProductsWithoutValidCoupons(marketId: Long, productName: String): List<Product>
}