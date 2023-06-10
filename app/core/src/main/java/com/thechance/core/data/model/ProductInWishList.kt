package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductInWishList(
    val productId: Long,
    val name: String,
    val price: Double
)
