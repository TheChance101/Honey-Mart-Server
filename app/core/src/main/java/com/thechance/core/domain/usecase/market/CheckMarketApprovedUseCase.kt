package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class CheckMarketApprovedUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(ownerId: Long?, role: String?): Long {
        val marketId = repository.getMarketIdByOwnerId(ownerId = ownerId!!)
        return if (isInvalidId(ownerId) || !isValidRole(MARKET_OWNER_ROLE, role)) {
            throw InvalidOwnerIdException()
        } else if (isInvalidId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            val market = repository.getMarket(marketId!!)
            if (market == null) {
                throw IdNotFoundException()
            } else if (market.isDeleted) {
                throw MarketDeletedException()
            } else {
                if (market.isApproved) {
                    market.marketId
                } else {
                    throw MarketNotApprovedException()
                }
            }
        }
    }
}