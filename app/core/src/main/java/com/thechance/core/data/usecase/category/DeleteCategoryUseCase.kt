package com.thechance.core.data.usecase.category

import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.utils.InvalidCategoryIdException
import org.koin.core.component.KoinComponent

class DeleteCategoryUseCase(private val categoryService: CategoryService) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?): Boolean {
        return if (checkId(categoryId)) {
            throw InvalidCategoryIdException()
        } else {
            categoryService.delete(categoryId)
        }
    }

    private fun checkId(id: Long?): Boolean {
        return id == null
    }
}