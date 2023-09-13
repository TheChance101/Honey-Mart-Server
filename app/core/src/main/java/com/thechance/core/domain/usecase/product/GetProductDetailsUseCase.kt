package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.domain.usecase.review.ReviewUseCaseContainer
import com.thechance.core.entity.product.ProductWithReviews
import com.thechance.core.utils.IdNotFoundException
import com.thechance.core.utils.InvalidProductIdException
import com.thechance.core.utils.ProductDeletedException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetProductDetailsUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    private val reviewUseCaseContainer: ReviewUseCaseContainer by inject()
    suspend operator fun invoke(productId: Long?): ProductWithReviews {
        return if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else {
            val isProductDeleted = repository.isProductDeleted(productId!!)
            if (isProductDeleted == null) {
                throw IdNotFoundException()
            } else if (isProductDeleted) {
                throw ProductDeletedException()
            } else {
                val product = repository.getProduct(productId)
                ProductWithReviews(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    image = product.images,
                    marketId = product.marketId,
                    reviews = reviewUseCaseContainer.getProductReviewsUseCase(product.id, 1)
                )
            }
        }
    }
}