package com.thechance.core.data.usecase.category

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.CategoryDeletedException
import com.thechance.core.data.utils.InvalidCategoryIdException
import com.thechance.core.data.utils.isValidId
import org.koin.core.component.KoinComponent

class DeleteCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?): Boolean {
        return if (isValidId(categoryId)) {
            throw InvalidCategoryIdException()
        }else if (repository.isCategoryDeleted(categoryId!!)) {
            throw CategoryDeletedException()
        } else {
            repository.deleteCategory(categoryId)
        }
    }
}