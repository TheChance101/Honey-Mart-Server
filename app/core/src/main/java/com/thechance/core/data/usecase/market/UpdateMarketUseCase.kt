package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import com.thechance.core.data.utils.checkName
import com.thechance.core.data.utils.isValidId
import org.koin.core.component.KoinComponent

class UpdateMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketId: Long?, marketName: String?): Market {
        return if (isValidId(marketId)) {
            throw InvalidMarketIdException()
        } else if (checkName(marketName)) {
            throw InvalidMarketNameException()
        } else if (repository.isMarketDeleted(marketId!!)) {
            throw MarketDeletedException()
        } else {
            repository.updateMarket(marketId, marketName!!)
        }
    }
}