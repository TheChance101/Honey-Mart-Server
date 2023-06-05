package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.service.MarketService
import org.koin.core.component.KoinComponent

class GetMarketsUseCase(private val marketService: MarketService) : KoinComponent {
    suspend operator fun invoke(): List<Market> {
        return marketService.getAllMarkets()
    }
}