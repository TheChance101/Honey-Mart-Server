package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemModel(val productId:Long, val count:Int, val marketId:Long)
