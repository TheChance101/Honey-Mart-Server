package com.thechance.core.domain.usecase.category

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class UpdateCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?, marketOwnerId: Long?, role: String?
    ): Boolean {

        isValidInput(categoryId, categoryName, marketId, imageId, marketOwnerId, role)?.let { throw it }

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

    private fun isValidInput(
        categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?, marketOwnerId: Long?, role: String?
    ): Exception? {
        return if (categoryName == null && imageId == null) {
            InvalidInputException()
        } else if (categoryName != null && !isValidCategoryName(categoryName)) {
            InvalidCategoryNameException()
        } else if (isInvalidId(marketId) || isInvalidId(marketOwnerId)) {
            InvalidMarketIdException()
        } else if (isInvalidId(categoryId)) {
            InvalidCategoryIdException()
        } else if (!isValidRole(MARKET_OWNER_ROLE, role)) {
            InvalidOwnerIdException()
        } else {
            null
        }
    }

}

