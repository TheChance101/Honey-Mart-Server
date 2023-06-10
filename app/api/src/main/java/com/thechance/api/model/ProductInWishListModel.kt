package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductInWishListModel(val productId: Long, val name: String, val price: Double)
