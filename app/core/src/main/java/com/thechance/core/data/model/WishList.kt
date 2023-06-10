package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WishList(
    val products: List<ProductInWishList>
)