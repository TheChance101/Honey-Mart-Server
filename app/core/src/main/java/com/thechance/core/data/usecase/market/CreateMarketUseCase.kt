package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.service.MarketService
import com.thechance.core.data.utils.InvalidMarketNameException
import com.thechance.core.data.utils.checkName
import org.koin.core.component.KoinComponent

class CreateMarketUseCase(private val marketService: MarketService) : KoinComponent {

    suspend operator fun invoke(marketName: String?): Market {
        return if (checkName(marketName)) {
            throw InvalidMarketNameException()
        } else {
            marketService.createMarket(marketName)
        }
    }
}