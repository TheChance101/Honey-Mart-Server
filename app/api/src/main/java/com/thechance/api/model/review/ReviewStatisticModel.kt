package com.thechance.api.model.review

import kotlinx.serialization.Serializable

@Serializable
data class ReviewStatisticModel(
    val averageRating: Float,
    val reviewsCount: Int,
    val oneStarCount: Int,
    val twoStarsCount: Int,
    val threeStarsCount: Int,
    val fourStarsCount: Int,
    val fiveStarsCount: Int
)
