package com.thechance.core.data.usecase.category

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent

class UpdateCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?): Boolean {
        isValidInput(categoryId, categoryName, marketId, imageId)?.let { throw it }

        if (repository.isCategoryDeleted(categoryId!!)) {
            throw CategoryDeletedException()
        }

        if (repository.isMarketDeleted(marketId!!)) {
            throw MarketDeletedException()
        }

        return repository.updateCategory(categoryId, categoryName, marketId, imageId)
    }

    private fun isValidInput(categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?): Exception? {
        return when {
            checkName(categoryName) -> {
                InvalidCategoryNameException()
            }

            checkLetter(categoryName) -> {
                InvalidCategoryNameLettersException()
            }

            isValidId(marketId) -> {
                InvalidMarketIdException()
            }

            isValidId(categoryId) -> {
                InvalidCategoryIdException()
            }

            isValidId(imageId?.toLong()) -> {
                InvalidImageIdException()
            }

            else -> {
                null
            }
        }
    }

    private fun checkLetter(categoryName: String?): Boolean {
        return categoryName?.let {
            return !isValidString(it)
        } ?: true
    }
}
