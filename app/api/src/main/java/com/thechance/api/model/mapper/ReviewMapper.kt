package com.thechance.api.model.mapper

import com.thechance.api.model.review.ReviewModel
import com.thechance.api.model.review.ReviewStatisticModel
import com.thechance.api.model.review.ReviewsWithStatisticsModel
import com.thechance.api.utils.convertDateToMillis
import com.thechance.core.entity.review.Review
import com.thechance.core.entity.review.ReviewStatistic
import com.thechance.core.entity.review.ReviewsWithStatistics

internal fun Review.toApiReviewModel(): ReviewModel =
    ReviewModel(
        reviewId = this.reviewId,
        content = this.content,
        rating = this.rating,
        reviewDate = this.reviewDate.convertDateToMillis(),
        user = this.user.toApiUserModel()
    )

internal fun ReviewStatistic.toApiReviewStatisticModel(): ReviewStatisticModel =
    ReviewStatisticModel(
        averageRating = this.averageRating,
        reviewsCount = this.reviewsCount,
        oneStarCount = this.oneStarCount,
        twoStarsCount = this.twoStarsCount,
        threeStarsCount = this.threeStarsCount,
        fourStarsCount = this.fourStarsCount,
        fiveStarsCount = this.fiveStarsCount
    )

internal fun ReviewsWithStatistics.toApiReviewsWithStatisticsModel(): ReviewsWithStatisticsModel =
    ReviewsWithStatisticsModel(
        reviewStatistic = this.reviewStatistic.toApiReviewStatisticModel(),
        reviews = this.reviews.map { it.toApiReviewModel() }
    )