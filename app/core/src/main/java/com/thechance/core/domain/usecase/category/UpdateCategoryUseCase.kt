package com.thechance.core.domain.usecase.category

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
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
            isValidCategoryName(categoryName) -> {
                InvalidCategoryNameException()
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

}
