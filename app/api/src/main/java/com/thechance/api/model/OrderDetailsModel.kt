package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderDetailsModel(
    val orderId: Long,
    val userId: Long,
    val marketId: Long,
    val products: List<ProductInOrderModel>,
    val totalPrice: Double,
    val date: String,
    val state: Int
)
