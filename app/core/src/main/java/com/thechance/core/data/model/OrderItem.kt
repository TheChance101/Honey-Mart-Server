package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val productId:Long,
    val count:Int,
    val marketId:Long
)
