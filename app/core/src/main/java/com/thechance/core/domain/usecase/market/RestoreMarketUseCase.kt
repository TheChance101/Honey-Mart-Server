package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class RestoreMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketId: Long?): Boolean {
        return if (isInvalidId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            val isDeleted = repository.isMarketDeleted(marketId!!)
            if (isDeleted == null) {
                throw IdNotFoundException()
            } else if (isDeleted) {
                repository.restoreMarket(marketId)
            } else {
                throw MarketAlreadyExistException()
            }
        }
    }
}