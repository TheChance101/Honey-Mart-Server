package com.thechance.core.domain.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.usecase.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import com.thechance.core.utils.*
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidUsername
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