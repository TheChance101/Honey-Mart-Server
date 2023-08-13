package com.thechance.core.entity.coupon

import java.time.LocalDateTime

data class Coupon(
    val couponId: Long,
    val productId: Long,
    val count: Int,
    val discountPercentage: Double,
    val expirationDate: LocalDateTime
)
