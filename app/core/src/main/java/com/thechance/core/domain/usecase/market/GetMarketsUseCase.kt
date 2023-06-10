package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Market
import org.koin.core.component.KoinComponent

class GetMarketsUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(): List<Market> {
        return repository.getAllMarkets()
    }
}