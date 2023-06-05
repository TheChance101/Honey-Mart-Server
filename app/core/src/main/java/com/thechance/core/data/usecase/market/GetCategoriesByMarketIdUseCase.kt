package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Category
import com.thechance.core.data.service.MarketService
import com.thechance.core.data.utils.InvalidMarketIdException
import com.thechance.core.data.utils.checkId
import org.koin.core.component.KoinComponent

class GetCategoriesByMarketIdUseCase(private val marketService: MarketService) : KoinComponent {

    suspend operator fun invoke(marketId: Long?): List<Category> {
        return if (checkId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            marketService.getCategoriesByMarket(marketId)
        }
    }
}