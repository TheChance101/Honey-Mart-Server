package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Order
import com.thechance.core.utils.InvalidUserIdException
import com.thechance.core.utils.MARKET_OWNER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class GetOrdersForMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketId: Long?, role: String?): List<Order> {
        return if ((isInvalidId(marketId) || !isValidRole(MARKET_OWNER_ROLE, role))) {
            throw InvalidUserIdException()
        } else {
            repository.getAllOrdersForMarket(marketId!!)
        }
    }
}