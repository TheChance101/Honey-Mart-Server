package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductWithAverageRatingModel(
    val id: Long,
    val name: String,
    val description: String?,
    val price: Double,
    val images: List<String>,
    val averageRating: Float
)
