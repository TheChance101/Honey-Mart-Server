package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Market
import com.thechance.core.utils.InvalidMarketNameException
import com.thechance.core.utils.isValidateMarketName
import org.koin.core.component.KoinComponent

class CreateMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(marketName: String?): Market {
        return if (!isValidateMarketName(marketName)) {
            throw InvalidMarketNameException()
        } else {
            repository.createMarket(marketName!!)
        }
    }

}