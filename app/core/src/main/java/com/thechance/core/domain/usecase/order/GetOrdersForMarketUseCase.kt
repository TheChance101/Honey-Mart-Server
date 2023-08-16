package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.order.MarketOrder
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class GetOrdersForMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(ownerId: Long?, role: String?, state: Int?): List<MarketOrder> {
        return if ((isInvalidId(ownerId) || !isValidRole(MARKET_OWNER_ROLE, role))) {
            throw InvalidUserIdException()
        } else {
            val marketId = repository.getMarketIdByOwnerId(ownerId!!)
            if (marketId == null) {
                throw UnauthorizedException()
            } else if (state in ORDER_STATUS_PENDING..ORDER_STATUS_CANCELED_BY_OWNER && state != null) {
                repository.getOrdersForMarket(marketId, state)
            } else {
                repository.getAllOrdersForMarket(marketId)
            }
        }
    }
}