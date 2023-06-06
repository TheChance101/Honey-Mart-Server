package com.thechance.core.data.usecase.category

import com.thechance.core.data.model.Product
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.CategoryDeletedException
import com.thechance.core.data.utils.InvalidCategoryIdException
import com.thechance.core.data.utils.isValidId
import org.koin.core.component.KoinComponent

class GetAllCategoriesUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?): List<Product> {
        return if (isValidId(categoryId)) {
            throw InvalidCategoryIdException()
        } else {
            if (!repository.isCategoryDeleted(categoryId!!)) {
                repository.getAllProductsInCategory(categoryId)
            } else {
                throw CategoryDeletedException()
            }
        }
    }
}