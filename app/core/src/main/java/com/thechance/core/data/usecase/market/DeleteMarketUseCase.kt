package com.thechance.core.data.usecase.market

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidMarketIdException
import com.thechance.core.data.utils.MarketDeletedException
import com.thechance.core.data.utils.isValidId
import org.koin.core.component.KoinComponent

class DeleteMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketId: Long?): Boolean {
        return if (isValidId(marketId)) {
            throw InvalidMarketIdException()
        } else if (repository.isMarketDeleted(marketId!!)) {
            throw MarketDeletedException()
        } else {
            repository.deleteMarket(marketId)
        }
    }
}