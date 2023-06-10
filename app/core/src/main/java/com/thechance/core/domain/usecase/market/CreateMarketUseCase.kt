package com.thechance.core.domain.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.usecase.repository.HoneyMartRepository
import com.thechance.core.utils.InvalidMarketNameException
import com.thechance.core.utils.isValidUsername
import org.koin.core.component.KoinComponent

class CreateMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(marketName: String?): Market {
        return if (isValidUsername(marketName)) {
            throw InvalidMarketNameException()
        } else {
            repository.createMarket(marketName!!)
        }
    }
}