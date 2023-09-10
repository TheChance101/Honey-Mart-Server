package com.thechance.core.entity.review

data class ReviewStatistic(
    val averageRating: Float,
    val reviewsCount: Int,
    val oneStarCount: Int,
    val twoStarsCount: Int,
    val threeStarsCount: Int,
    val fourStarsCount: Int,
    val fiveStarsCount: Int
)
