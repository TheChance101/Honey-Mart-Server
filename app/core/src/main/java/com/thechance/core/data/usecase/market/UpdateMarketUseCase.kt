package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.service.MarketService
import com.thechance.core.data.utils.InvalidMarketIdException
import com.thechance.core.data.utils.InvalidMarketNameException
import com.thechance.core.data.utils.checkId
import com.thechance.core.data.utils.checkName
import org.koin.core.component.KoinComponent

class UpdateMarketUseCase(private val marketService: MarketService) : KoinComponent {
    suspend operator fun invoke(marketId: Long?, marketName: String?): Market {
        return if (checkId(marketId)) {
            throw InvalidMarketIdException()
        } else if (checkName(marketName)) {
            throw InvalidMarketNameException()
        } else {
            marketService.updateMarket(marketId, marketName)
        }
    }
}