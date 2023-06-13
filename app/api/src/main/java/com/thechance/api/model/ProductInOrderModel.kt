package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductInOrderModel(val id: Long, val name: String, val count: Int, val price: Double)