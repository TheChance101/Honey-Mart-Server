package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WishList(
    val id: Long,
    val userId:Long,
    //val products: List<ProductInWishList>,
)