package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UserOrderModel(val orderId: Long, val totalPrice: Double, val state: Int, val date: Long, val market: MarketModel)