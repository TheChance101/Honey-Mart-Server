package com.thechance.core.domain.usecase.category

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.CategoryDeletedException
import com.thechance.core.utils.IdNotFoundException
import com.thechance.core.utils.InvalidCategoryIdException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class DeleteCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?): Boolean {
        return if (isInvalidId(categoryId)) {
            throw InvalidCategoryIdException()
        } else {
            val isCategoryDeleted = repository.isCategoryDeleted(categoryId!!)
            if (isCategoryDeleted == null) {
                throw IdNotFoundException()
            } else if (isCategoryDeleted) {
                throw CategoryDeletedException()
            } else {
                repository.deleteCategory(categoryId)
            }
        }
    }
}