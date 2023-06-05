package com.thechance.core.data.usecase.category

import com.thechance.core.data.model.Category
import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.utils.*

class CreateCategoryUseCase(private val categoryService: CategoryService) {
    suspend operator fun invoke(categoryName: String?, marketId: Long?, imageId: Int?): Category {
        val exception = mutableListOf<Exception>()

        checkCategoryName(categoryName)?.let {
            exception.add(it)
        }

        checkLetter(categoryName)?.let {
            exception.add(it)
        }

        checkMarketId(marketId)?.let {
            exception.add(it)
        }

        checkImageId(imageId)?.let {
            exception.add(it)
        }

        return if (exception.isEmpty()) {
            categoryService.create(categoryName, marketId, imageId)
        } else {
            throw CreateCategoryException(exception)
        }
    }

    private fun checkCategoryName(categoryName: String?): Exception? {
        return categoryName?.let {
            return if (it.length !in 6..20) {
                InvalidCategoryNameException()
            } else {
                null
            }
        } ?: EmptyCategoryNameException()
    }

    private fun checkLetter(categoryName: String?): Exception? {
        return categoryName?.let {
            return if (!isValidStringInput(it)) {
                InvalidCategoryNameLettersException()
            } else {
                null
            }
        }
    }

    private fun checkMarketId(marketId: Long?): Exception? {
        return if (marketId == null) {
            InvalidMarketIdException()
        } else {
            null
        }
    }

    private fun checkImageId(imageId: Int?): Exception? {
        return if (imageId == null) {
            InvalidImageIdException()
        } else {
            null
        }
    }
}

class CreateCategoryException(val exceptions: List<Exception>) : Exception()
