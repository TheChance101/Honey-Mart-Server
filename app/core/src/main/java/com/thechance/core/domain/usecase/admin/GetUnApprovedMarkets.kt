package com.thechance.core.domain.usecase.admin

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.market.Market
import org.koin.core.component.KoinComponent

class GetUnApprovedMarkets(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(): List<Market> {
        return repository.getUnApprovedMarkets()
    }

}