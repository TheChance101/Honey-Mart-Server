package com.thechance.core.data.usecase.market

import com.thechance.core.data.service.MarketService
import com.thechance.core.data.utils.InvalidMarketIdException
import org.koin.core.component.KoinComponent

class DeleteMarketUseCase(private val marketService: MarketService) : KoinComponent {
    suspend operator fun invoke(marketId: Long?): Boolean {
        return if (checkId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            marketService.deleteMarket(marketId)
        }
    }

    private fun checkId(id: Long?): Boolean {
        return id == null
    }
}