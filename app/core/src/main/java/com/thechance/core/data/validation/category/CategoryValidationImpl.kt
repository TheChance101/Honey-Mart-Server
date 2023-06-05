package com.thechance.core.data.validation.category

import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent

class CategoryValidationImpl : CategoryValidation, KoinComponent {

    override fun checkCreateValidation(categoryName: String?, marketId: Long?, imageId: Int?): Exception? {
        val exception = mutableListOf<String>()

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
            null
        } else {
            InvalidInputException()
        }
    }

    override fun checkUpdateValidation(
        categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?
    ) {

        checkId(marketId)

        checkId(categoryId)

        checkCategoryFields(categoryName, imageId)

        if (!categoryName.isNullOrEmpty()) {
            checkCategoryName(categoryName)
            checkLetter(categoryName)
        } else {
            checkImageId(imageId)
        }
    }

    private fun checkCategoryFields(categoryName: String?, imageId: Int?): String? {
        return if (categoryName.isNullOrEmpty() && imageId == null) {
            throw UpdateException()
        } else {
            null
        }
    }

    override fun checkId(categoryId: Long?): String? {
        return if (categoryId == null) {
            throw CategoryInvalidIDException()
        } else {
            null
        }
    }

    private fun checkCategoryName(categoryName: String?): String? {
        return categoryName?.let {
            return if (it.length !in 6..20) {
                throw CategoryNameLengthException()
            } else {
                null

            }
        } ?: throw CategoryNameEmptyException()
    }

    private fun checkLetter(categoryName: String?): String? {
        return categoryName?.let {
            return if (!isValidStringInput(it)) {
                throw CategoryNameLetterException()
            } else {
                null
            }
        }
    }

    private fun checkMarketId(marketId: Long?): String? {
        return if (marketId == null) {
            throw CategoryInvalidIDException()
        } else {
            null
        }
    }

    private fun checkImageId(imageId: Int?): String? {
        return if (imageId == null) {
           throw CategoryImageIDException()
        } else {
            null
        }
    }
}