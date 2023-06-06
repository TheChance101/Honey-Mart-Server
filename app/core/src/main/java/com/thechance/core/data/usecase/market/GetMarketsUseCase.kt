package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.repository.HoneyMartRepository
import org.koin.core.component.KoinComponent

class GetMarketsUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(): List<Market> {
        return repository.getAllMarkets()
    }
}