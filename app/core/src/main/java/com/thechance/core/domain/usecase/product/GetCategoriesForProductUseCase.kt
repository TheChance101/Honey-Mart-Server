package com.thechance.core.domain.usecase.product

import com.thechance.core.data.model.Category
import com.thechance.core.data.usecase.repository.HoneyMartRepository
import com.thechance.core.utils.IdNotFoundException
import com.thechance.core.utils.InvalidProductIdException
import com.thechance.core.utils.ProductDeletedException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetCategoriesForProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(productId: Long?): List<Category> {
        return if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else {
            val isProductDeleted = repository.isProductDeleted(productId!!)
            if (isProductDeleted == null) {
                throw IdNotFoundException()
            } else if (isProductDeleted) {
                throw ProductDeletedException()
            } else {
                repository.getAllCategoryForProduct(productId)
            }
        }
    }
}