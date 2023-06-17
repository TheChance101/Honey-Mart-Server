package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Order
import com.thechance.core.utils.*
import com.thechance.core.utils.MARKET_OWNER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class GetOrdersForMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(ownerId: Long?, role: String?): List<Order> {
        return if ((isInvalidId(ownerId) || !isValidRole(MARKET_OWNER_ROLE, role))) {
            throw InvalidUserIdException()
        } else {
            val marketId = repository.getMarketIdByOwnerId(ownerId!!)
            if (marketId == null) {
                throw UnauthorizedException()
            } else {
                repository.getAllOrdersForMarket(marketId)
            }
        }
    }
}