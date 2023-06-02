package com.thechance.core.data.validation.category

import com.thechance.api.utils.InvalidInputException
import com.thechance.api.utils.isValidStringInput
import org.jetbrains.exposed.sql.ResultRow
import org.koin.core.component.KoinComponent

class CategoryValidationImpl : CategoryValidation, KoinComponent {

    override fun checkCreateValidation(categoryName: String, marketId: Long?, imageId: Int?): Exception? {
        val exception = mutableListOf<String>()

        checkCategoryName(categoryName)?.let {
            exception.add(it)
        }

        checkLetter(categoryName)?.let {
            exception.add(it)
        }

        checkMarketId(marketId).let {
            exception.add(it)
        }

        checkImageId(imageId).let {
            exception.add(it)
        }

        return if (exception.isEmpty()) {
            null
        } else {
            InvalidInputException(exception.joinToString(" ,\n "))
        }
    }


    private fun checkCategoryName(categoryName: String?): String? {
        return categoryName?.let {
            return if (it.length !in 6..20) {
                "The category name should have a length greater than 6 and shorter than 20 characters ."
            } else {
                null

            }
        } ?: "The category name can not be empty"
    }

    private fun checkLetter(categoryName: String?): String? {
        return categoryName?.let {
            return if (!isValidStringInput(it)) {
                "The category name must contain only letters."
            } else {
                null
            }
        }
    }

    private fun checkMarketId(marketId: Long?): String {
        return marketId?.let {
            null
        } ?: "Invalid marketID"
    }

    private fun checkImageId(imageId: Int?): String {
        return imageId?.let {
            null
        } ?: "Invalid imageID"
    }
}