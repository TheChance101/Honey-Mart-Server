package com.thechance.api.model.order

import com.thechance.api.model.UserModel
import kotlinx.serialization.Serializable

@Serializable
data class MarketOrderModel(val orderId: Long, val totalPrice: Double, val state: Int, val date: Long, val user: UserModel)