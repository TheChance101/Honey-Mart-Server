package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Market
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class UpdateMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketId: Long?, marketName: String?, marketOwnerId: Long?, role: String?): Market {

        isValidInput(marketId, marketName, marketOwnerId, role)?.let { throw it }

        val isDeleted = repository.isMarketDeleted(marketId!!)
        return if (isDeleted == null) {
            throw IdNotFoundException()
        } else if (isDeleted) {
            throw MarketDeletedException()
        } else {
            repository.updateMarket(marketId, marketName!!)
        }
    }


    private fun isValidInput(marketId: Long?, marketName: String?, marketOwnerId: Long?, role: String?): Exception? {
        return if (isInvalidId(marketId) || isInvalidId(marketOwnerId)) {
            InvalidMarketIdException()
        } else if (!isValidateMarketName(marketName)) {
            InvalidMarketNameException()
        } else if (!isValidRole(MARKET_OWNER_ROLE, role)) {
            InvalidOwnerIdException()
        } else {
            null
        }
    }
}