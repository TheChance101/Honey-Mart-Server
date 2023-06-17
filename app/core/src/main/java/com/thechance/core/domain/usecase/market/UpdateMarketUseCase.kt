package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Market
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class UpdateMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketName: String?, marketOwnerId: Long?, role: String?): Market {


        isValidInput(marketName, marketOwnerId, role)?.let { throw it }

        val marketId = repository.getMarketIdByOwnerId(ownerId = marketOwnerId!!)

        return if (marketId == null) {
            throw InvalidMarketIdException()
        } else {
            val isDeleted = repository.isMarketDeleted(marketId)
            if (isDeleted == null) {
                throw IdNotFoundException()
            } else if (isDeleted) {
                throw MarketDeletedException()
            } else {
                repository.updateMarket(marketId, marketName!!)
            }
        }
    }


    private fun isValidInput(marketName: String?, marketOwnerId: Long?, role: String?): Exception? {
        return if (isInvalidId(marketOwnerId)) {
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