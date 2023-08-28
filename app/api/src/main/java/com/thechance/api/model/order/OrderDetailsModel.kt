package com.thechance.api.model.order

import com.thechance.api.model.ProductWithCount
import kotlinx.serialization.Serializable

@Serializable
data class OrderDetailsModel(
    val orderId: Long,
    val userId: Long,
    val marketId: Long,
    val products: List<ProductWithCount>,
    val totalPrice: Double,
    val date: Long,
    val state: Int
)
