package com.thechance.core.domain.usecase.category

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class DeleteCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?, marketOwnerId: Long?, role: String?): Boolean {

        isValidInput(categoryId, marketOwnerId, role)?.let { throw it }

        if (isMarketOwner(marketOwnerId!!, categoryId!!)) {
            val isCategoryDeleted = repository.isCategoryDeleted(categoryId!!)
            return if (isCategoryDeleted == null) {
                throw IdNotFoundException()
            } else if (isCategoryDeleted) {
                throw CategoryDeletedException()
            } else {
                repository.deleteCategory(categoryId)
            }
        } else {
            throw UnauthorizedException()
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


    private suspend fun isMarketOwner(marketOwnerId: Long, categoryId: Long): Boolean {
        val marketId = repository.getMarketIdByCategoryId(categoryId)
        return repository.getOwnerIdByMarketId(marketId) == marketOwnerId
    }
}