package com.thechance.core.data.model

import kotlinx.serialization.Serializable

data class Order(
    val orderId: Long,
    val products: List<OrderProduct>
)
data class OrderProduct(
    val productId: Long,
    val count: Int
)

@Serializable
data class OrderWithPrice(
    val orderId: Long,
    val totalPrice: Double
)