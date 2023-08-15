package com.thechance.core.domain.usecase.admin

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.InvalidMarketIdException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class ApproveMarketUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(marketId: Long, isApproved: Boolean): Boolean {
        return if (isInvalidId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            repository.approveMarket(marketId,isApproved)
        }

    }
}