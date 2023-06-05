package com.thechance.core.data.usecase.category

import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent
import java.util.regex.Pattern

class UpdateCategoryUseCase(private val categoryService: CategoryService) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?): Boolean {
        return when {
            checkCategoryName(categoryName) -> {
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
            checkImageId(imageId) -> {
                throw InvalidImageIdException()
            }
            else -> {
                categoryService.update(categoryId, categoryName, marketId, imageId)
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

    private fun checkId(id: Long?): Boolean {
        return id == null
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
