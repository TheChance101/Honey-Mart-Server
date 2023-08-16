package com.thechance.core.entity.coupon

import com.thechance.core.entity.Product
import java.time.LocalDateTime

data class Coupon(
    val couponId: Long,
    val count: Int,
    val discountPercentage: Double,
    val expirationDate: LocalDateTime,
    val product: Product,
)
