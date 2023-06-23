package com.thechance.api.model

import com.thechance.api.model.market.MarketModel
import kotlinx.serialization.Serializable

@Serializable
data class UserOrderModel(
    val orderId: Long,
    val totalPrice: Double,
    val state: Int,
    val date: Long,
    val market: MarketModel,
    val numItems: Long
)