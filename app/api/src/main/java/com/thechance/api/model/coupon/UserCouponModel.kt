package com.thechance.api.model.coupon

import com.thechance.api.model.ProductModel
import kotlinx.serialization.Serializable

@Serializable
data class UserCouponModel(
    val couponId: Long,
    val count: Int,
    val discountPercentage: Double,
    val expirationDate: String,
    val product: ProductModel,
    val isClipped: Boolean,
    val isUsed: Boolean
)
