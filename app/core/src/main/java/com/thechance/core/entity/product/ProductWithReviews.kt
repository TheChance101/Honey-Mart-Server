package com.thechance.core.entity.product

import com.thechance.core.entity.Image
import com.thechance.core.entity.review.ReviewsWithStatistics

data class ProductWithReviews(
    val id: Long,
    val name: String,
    val description: String?,
    val price: Double,
    val image: List<Image>,
    val marketId: Long,
    val reviews: ReviewsWithStatistics
)
