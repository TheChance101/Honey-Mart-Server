package com.thechance.core.entity.review

data class ReviewsWithStatistics(
    val reviewStatistic: ReviewStatistic,
    val reviews: List<Review>
)
