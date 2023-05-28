package com.the_chance.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val categoryId: Long,
    val marketId : Long,
    val categoryName: String,
    val products:List<Product> = emptyList()
)
