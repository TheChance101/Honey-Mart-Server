package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class WishListModel(val products: List<ProductInWishListModel>)