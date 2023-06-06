package com.thechance.core.data.usecase.product

import com.thechance.core.data.model.Category
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidProductIdException
import com.thechance.core.data.utils.ProductDeletedException
import com.thechance.core.data.utils.isValidId
import org.koin.core.component.KoinComponent

class GetCategoriesForProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(productId: Long?): List<Category> {
        return if (isValidId(productId)) {
            throw InvalidProductIdException()
        } else if (repository.isProductDeleted(productId!!)) {
            throw ProductDeletedException()
        } else {
            repository.getAllCategoryForProduct(productId)
        }
    }
}