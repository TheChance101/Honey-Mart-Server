package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: Long,
    val marketId: Long,
    val orderDate: String,
    val totalPrice: Double,
    val isPaid: Boolean = false,
    val isCanceled: Boolean = false
)