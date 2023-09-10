package com.thechance.api.model.review

import kotlinx.serialization.Serializable

@Serializable
data class ReviewsWithStatisticsModel(
    val reviewStatistic: ReviewStatisticModel,
    val reviews: List<ReviewModel>
)
