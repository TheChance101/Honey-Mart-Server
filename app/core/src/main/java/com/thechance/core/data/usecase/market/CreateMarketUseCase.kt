package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidMarketNameException
import com.thechance.core.data.utils.isValidUsername
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