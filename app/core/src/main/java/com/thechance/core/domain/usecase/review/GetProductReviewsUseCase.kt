package com.thechance.core.domain.usecase.review

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.review.ReviewsWithStatistics
import com.thechance.core.utils.IdNotFoundException
import com.thechance.core.utils.InvalidProductIdException
import com.thechance.core.utils.ProductDeletedException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetProductReviewsUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(productId: Long?): ReviewsWithStatistics {
        val isProductDeleted = if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else {
            repository.isProductDeleted(productId!!)
        }

        return when {
            isProductDeleted == null -> {
                throw IdNotFoundException()
            }

            isProductDeleted -> {
                throw ProductDeletedException()
            }

            else -> {
                ReviewsWithStatistics(
                    repository.getReviewsStatisticsForProduct(productId),
                    repository.getProductReviews(productId)
                )
            }
        }
    }
}