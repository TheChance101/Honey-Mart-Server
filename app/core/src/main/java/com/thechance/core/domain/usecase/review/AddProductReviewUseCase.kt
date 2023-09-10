package com.thechance.core.domain.usecase.review

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class AddProductReviewUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        userId: Long?,
        productId: Long?,
        orderId: Long?,
        content: String?,
        rating: Int?,
        role: String?
    ): Boolean {
        isValidInput(userId, productId, orderId, content, rating, role)?.let { throw it }
        return repository.addProductReview(
            userId = userId!!,
            productId = productId!!,
            orderId = orderId!!,
            content = content!!,
            rating = rating!!
        )
    }

    private suspend fun isValidInput(
        productId: Long?,
        userId: Long?,
        orderId: Long?,
        content: String?,
        rating: Int?,
        role: String?,
    ): Exception? {
        val isProductDeleted = repository.isProductDeleted(productId!!)
        return when {
            isProductDeleted == null -> {
                throw IdNotFoundException()
            }

            isProductDeleted -> {
                throw ProductDeletedException()
            }

            isInvalidId(productId) -> {
                InvalidProductIdException()
            }

            (isInvalidId(orderId) || !repository.isOrderExist(orderId!!)) -> {
                InvalidOrderIdException()
            }

            isInValidDescription(content) -> {
                InvalidReviewContentException()
            }

            isInvalidRating(rating) -> {
                InvalidRatingException()
            }

            isInvalidId(userId) -> {
                InvalidUserIdException()
            }

            !isValidRole(NORMAL_USER_ROLE, role) -> {
                InvalidUserIdException()
            }

            else -> {
                null
            }
        }
    }
}