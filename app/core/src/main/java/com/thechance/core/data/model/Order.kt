package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val orderId: Long,
    val totalPrice: Double
)