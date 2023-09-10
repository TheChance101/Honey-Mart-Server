package com.thechance.api.model

import com.thechance.api.model.review.ReviewsWithStatisticsModel
import kotlinx.serialization.Serializable

@Serializable
data class ProductWithReviewsModel(
    val id: Long,
    val name: String,
    val description: String?,
    val price: Double,
    val images: List<String>,
    val reviews: ReviewsWithStatisticsModel
)
