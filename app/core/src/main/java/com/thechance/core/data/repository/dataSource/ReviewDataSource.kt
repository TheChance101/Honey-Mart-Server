package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.review.Review
import com.thechance.core.entity.review.ReviewStatistic

interface ReviewDataSource {
    suspend fun addProductReview(
        userId: Long,
        productId: Long,
        orderId: Long,
        content: String,
        rating: Int
    ): Boolean

    suspend fun getProductReviews(productId: Long, page: Int): List<Review>
    suspend fun getReviewsStatisticsForProduct(productId: Long): ReviewStatistic
}