package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CartModel(val products: List<ProductInCartModel>, val total: Double)

