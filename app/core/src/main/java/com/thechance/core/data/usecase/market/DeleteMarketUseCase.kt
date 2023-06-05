package com.thechance.core.data.usecase.market

import com.thechance.core.data.service.MarketService
import com.thechance.core.data.utils.InvalidMarketIdException
import com.thechance.core.data.utils.checkId
import org.koin.core.component.KoinComponent

class DeleteMarketUseCase(private val marketService: MarketService) : KoinComponent {
    suspend operator fun invoke(marketId: Long?): Boolean {
        return if (checkId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            marketService.deleteMarket(marketId)
        }
    }
}