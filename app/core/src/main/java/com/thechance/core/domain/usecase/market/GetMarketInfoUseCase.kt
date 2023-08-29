package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.market.MarketRequest
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class GetMarketInfoUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(ownerId: Long?, role: String?): MarketRequest {
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
                MarketRequest(
                    marketId = market.marketId,
                    marketName = market.marketName,
                    imageUrl = market.imageUrl,
                    description = market.description,
                    address = market.address,
                    marketStatus = market.marketStatus,
                    isApproved = true,
                    ownerEmail = "",
                    ownerName = "",
                )
            }
        }
    }
}