package com.thechance.core.domain.usecase.category

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Category
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent
import java.util.regex.Pattern

class CreateCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(categoryName: String?, marketId: Long?, imageId: Int?): Category {

        isValidInput(categoryName, marketId, imageId)?.let { throw it }

        val isMarketDeleted = repository.isMarketDeleted(marketId!!)
        return if (isMarketDeleted == null) {
            throw IdNotFoundException()
        } else if (isMarketDeleted) {
            throw MarketDeletedException()
        } else {
            if (repository.isCategoryNameUnique(categoryName!!)) {
                repository.createCategory(categoryName, marketId, imageId!!)
            } else {
                throw CategoryNameNotUniqueException()
            }
        }
    }

    private fun isValidInput(categoryName: String?, marketId: Long?, imageId: Int?): Exception? {
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
            return !isValidStringInput(it)
        } ?: true
    }

    private fun isValidStringInput(name: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z\\s]+$")
        val matcher = pattern.matcher(name)
        return matcher.matches()
    }
}
