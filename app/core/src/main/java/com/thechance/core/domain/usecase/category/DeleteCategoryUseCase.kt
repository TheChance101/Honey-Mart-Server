package com.thechance.core.domain.usecase.category

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class DeleteCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?, marketOwnerId: Long?, role: String?): Boolean {

        isValidInput(categoryId, marketOwnerId, role)?.let { throw it }

        val isCategoryDeleted = repository.isCategoryDeleted(categoryId!!)
        return if (isCategoryDeleted == null) {
            throw IdNotFoundException()
        } else if (isCategoryDeleted) {
            throw CategoryDeletedException()
        } else {
            repository.deleteCategory(categoryId)
        }
    }

    private fun isValidInput(categoryId: Long?, marketOwnerId: Long?, role: String?): Exception? {
        return if (isInvalidId(categoryId) || isInvalidId(marketOwnerId)) {
            IdNotFoundException()
        } else if (!isValidRole(MARKET_OWNER_ROLE, role)) {
            throw InvalidMarketIdException()
        } else {
            null
        }
    }

}