package com.thechance.core.data.usecase.category

import com.thechance.core.data.model.Category
import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent
import java.util.regex.Pattern

class CreateCategoryUseCase(private val categoryService: CategoryService) : KoinComponent {
    suspend operator fun invoke(categoryName: String?, marketId: Long?, imageId: Int?): Category {
        return when {
            checkCategoryName(categoryName) -> {
                throw InvalidCategoryNameException()
            }
            checkLetter(categoryName) -> {
                throw InvalidCategoryNameLettersException()
            }
            checkMarketId(marketId) -> {
                throw InvalidMarketIdException()
            }
            checkImageId(imageId) -> {
                throw InvalidImageIdException()
            }
            else -> {
                categoryService.create(categoryName, marketId, imageId)
            }
        }
    }

    private fun checkCategoryName(categoryName: String?): Boolean {
        return categoryName?.let {
            return it.length !in 6..20
        } ?: true
    }

    private fun checkLetter(categoryName: String?): Boolean {
        return categoryName?.let {
            return !isValidStringInput(it)
        } ?: true
    }

    private fun checkMarketId(marketId: Long?): Boolean {
        return marketId == null
    }

    private fun checkImageId(imageId: Int?): Boolean {
        return imageId == null
    }

    private fun isValidStringInput(name: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z\\s]+$")
        val matcher = pattern.matcher(name)
        return matcher.matches()
    }
}
