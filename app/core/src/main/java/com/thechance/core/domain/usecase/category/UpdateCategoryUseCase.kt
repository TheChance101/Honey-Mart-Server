package com.thechance.core.domain.usecase.category

import com.thechance.core.data.usecase.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import com.thechance.core.utils.*
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidString
import com.thechance.core.utils.isValidUsername
import org.koin.core.component.KoinComponent

class UpdateCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?): Boolean {
        isValidInput(categoryId, categoryName, marketId, imageId)?.let { throw it }

        val isCategoryDeleted = repository.isCategoryDeleted(categoryId!!)
        if (isCategoryDeleted == null) {
            throw IdNotFoundException()
        } else if (isCategoryDeleted) {
            throw CategoryDeletedException()

        }

        val isMarketDeleted = repository.isMarketDeleted(marketId!!)
        if (isMarketDeleted == null) {
            throw IdNotFoundException()
        } else if (isMarketDeleted) {
            throw MarketDeletedException()

        }

        return repository.updateCategory(categoryId, categoryName, marketId, imageId)
    }

    private fun isValidInput(categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?): Exception? {
        return when {
            isValidUsername(categoryName) -> {
                InvalidCategoryNameException()
            }

            checkLetter(categoryName) -> {
                InvalidCategoryNameLettersException()
            }

            isInvalidId(marketId) -> {
                InvalidMarketIdException()
            }

            isInvalidId(categoryId) -> {
                InvalidCategoryIdException()
            }

            isInvalidId(imageId?.toLong()) -> {
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
