package com.thechance.core.domain.usecase.product

import com.thechance.core.data.datasource.mapper.toProductWithAverageRating
import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.product.ProductWithAverageRating
import com.thechance.core.utils.IdNotFoundException
import com.thechance.core.utils.InvalidProductIdException
import com.thechance.core.utils.ProductDeletedException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetSimilarProductsUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(productId: Long?, page: Int): List<ProductWithAverageRating> {
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
                repository.getProductsInSameCategories(productId, page).map {
                    it.toProductWithAverageRating(
                        repository.getProductAverageRating(it.id)
                    )
                }
            }
        }
    }
}