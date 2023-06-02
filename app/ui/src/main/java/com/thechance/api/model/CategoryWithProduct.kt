package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryWithProduct(
    val categoryId: Long,
    val categoryName: String,
    val products: List<Product>,
)