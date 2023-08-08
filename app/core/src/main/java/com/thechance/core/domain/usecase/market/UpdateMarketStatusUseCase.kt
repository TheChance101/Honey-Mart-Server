package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.MARKET_OWNER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class UpdateMarketStatusUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(marketOwnerId: Long?, role: String?, status: Int?): Boolean {
        if (status != null && status in intArrayOf(1, 2)) {
            isValidInput(marketOwnerId, role)?.let { throw it }
            val marketId = repository.getMarketIdByOwnerId(ownerId = marketOwnerId!!)
            isValidMarket(marketId)?.let { throw it }
            return repository.updateMarketStatus(marketId!!, status == 1)
        } else {
            throw InvalidInputException()
        }
    }

    private fun isValidInput(marketOwnerId: Long?, role: String?): Exception? {
        return if (isInvalidId(marketOwnerId)) {
            InvalidMarketIdException()
        } else if (!isValidRole(MARKET_OWNER_ROLE, role)) {
            InvalidOwnerIdException()
        } else {
            null
        }
    }

    private suspend fun isValidMarket(marketId: Long?): Exception? {
        return if (marketId == null) {
            InvalidMarketIdException()
        } else {
            val isDeleted = repository.isMarketDeleted(marketId)
            if (isDeleted == null) {
                IdNotFoundException()
            } else if (isDeleted) {
                MarketDeletedException()
            } else {
                null
            }
        }
    }
}