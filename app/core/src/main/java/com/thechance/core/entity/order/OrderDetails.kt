package com.thechance.core.entity.order

import com.thechance.core.entity.ProductInCart
import java.time.LocalDateTime

data class OrderDetails(
    val orderId: Long,
    val userId: Long,
    val marketId: Long,
    val products: List<ProductInCart>,
    val totalPrice: Double,
    val date: LocalDateTime,
    val state: Int
)
