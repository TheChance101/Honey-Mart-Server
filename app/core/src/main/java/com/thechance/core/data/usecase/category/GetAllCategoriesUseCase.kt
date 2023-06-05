package com.thechance.core.data.usecase.category

import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product
import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.utils.InvalidCategoryIdException
import org.koin.core.component.KoinComponent

class GetAllCategoriesUseCase(private val categoryService: CategoryService) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?): List<Product> {
        return if (checkId(categoryId)) {
            throw InvalidCategoryIdException()
        } else {
            categoryService.getAllProductsInCategory(categoryId)
        }
    }

    private fun checkId(id: Long?): Boolean {
        return id == null
    }
}