package com.thechance.core.data.model

import kotlinx.serialization.Serializable


@Serializable
data class ProductInCart(val id: Long, val name: String, val quantity: Int, val price: Double)

@Serializable
data class Cart(
    val products: List<Product>,
    val total: Double
)

