package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CartModel(val products: List<ProductWithCount>, val total: Double)

