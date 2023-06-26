package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Category
import com.thechance.core.entity.market.MarketDetails
import com.thechance.core.utils.IdNotFoundException
import com.thechance.core.utils.InvalidMarketIdException
import com.thechance.core.utils.MarketDeletedException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetMarketDetailsUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(marketId: Long?): MarketDetails {
        return if (isInvalidId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            val market = repository.getMarket(marketId!!)
            if (market == null) {
                throw IdNotFoundException()
            } else if (market.isDeleted) {
                throw MarketDeletedException()
            } else {
                MarketDetails(market, repository.getCategoriesByMarket(marketId))
            }
        }
    }
}