package com.thechance.core.data.usecase.category

import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent

class UpdateCategoryUseCase(private val categoryService: CategoryService) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?): Boolean {
        return when {
            checkName(categoryName) -> {
                throw InvalidCategoryNameException()
            }

            checkLetter(categoryName) -> {
                throw InvalidCategoryNameLettersException()
            }

            checkId(marketId) -> {
                throw InvalidMarketIdException()
            }

            checkId(categoryId) -> {
                throw InvalidCategoryIdException()
            }

            checkId(imageId?.toLong()) -> {
                throw InvalidImageIdException()
            }

            else -> {
                categoryService.update(categoryId, categoryName, marketId, imageId)
            }
        }
    }

    private fun checkLetter(categoryName: String?): Boolean {
        return categoryName?.let {
            return !isValidString(it)
        } ?: true
    }
}
