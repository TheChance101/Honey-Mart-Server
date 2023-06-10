package com.thechance.core.domain.usecase.order

import com.thechance.core.data.model.Order
import com.thechance.core.domain.repository.HoneyMartRepository
import org.koin.core.component.KoinComponent

class GetOrdersForMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketId: Long?): List<Order> {
        return repository.getAllOrdersForMarket(marketId!!)
    }
}