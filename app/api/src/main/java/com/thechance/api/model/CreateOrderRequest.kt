package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderRequest(
    val marketId: String?,
    val orderDate: String?,
    val totalPrice: String?,
    val isPaid: String?,
    val products: List<OrderItemRequest>?
) {
    @Serializable
    data class OrderItemRequest(
        val productId: String,
        val quantity: String
    )
}
