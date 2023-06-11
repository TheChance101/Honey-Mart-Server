package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Market
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class UpdateMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketId: Long?, marketName: String?): Market {
        return if (isInvalidId(marketId)) {
            throw InvalidMarketIdException()
        } else if (isValidUsername(marketName)) {
            throw InvalidMarketNameException()
        } else {
            val isDeleted = repository.isMarketDeleted(marketId!!)
            if (isDeleted == null) {
                throw IdNotFoundException()
            } else if (isDeleted) {
                throw MarketDeletedException()
            } else {
                repository.updateMarket(marketId, marketName!!)
            }
        }
    }
}