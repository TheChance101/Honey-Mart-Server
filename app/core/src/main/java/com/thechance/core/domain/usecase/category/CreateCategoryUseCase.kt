package com.thechance.core.domain.usecase.category

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Category
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent
import java.util.regex.Pattern

class CreateCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        categoryName: String?, marketId: Long?, imageId: Int?, marketOwnerId: Long?, role: String?
    ): Category {

        isValidInput(categoryName, marketId, imageId, marketOwnerId, role)?.let { throw it }

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

    private fun isValidInput(
        categoryName: String?, marketId: Long?, imageId: Int?, marketOwnerId: Long?, role: String?
    ): Exception? {
        return when {
            !isValidCategoryName(categoryName) -> {
                InvalidCategoryNameException()
            }

            isInvalidId(marketId) -> {
                InvalidMarketIdException()
            }

            isInvalidId(imageId?.toLong()) -> {
                InvalidImageIdException()
            }

            isInvalidId(marketOwnerId) -> {
                InvalidImageIdException()
            }

            !isValidRole(MARKET_OWNER_ROLE, role) -> {
                InvalidMarketIdException()
            }

            else -> {
                null
            }
        }
    }

}
