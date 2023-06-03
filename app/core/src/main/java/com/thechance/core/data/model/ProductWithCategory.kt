package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductWithCategory(
    val id: Long,
    val name: String,
    val quantity: String?,
    val price: Double,
    val category: List<Category>
)