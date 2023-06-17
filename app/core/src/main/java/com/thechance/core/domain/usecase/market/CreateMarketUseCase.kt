package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Market
import com.thechance.core.utils.InvalidMarketNameException
import com.thechance.core.utils.InvalidOwnerIdException
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidMarketProductName
import org.koin.core.component.KoinComponent

class CreateMarketUseCase(private val repository: HoneyMartRepository, private val authRepository: AuthRepository) :
    KoinComponent {

    suspend operator fun invoke(marketName: String?, ownerId: Long?): Market {
        return if (!isValidMarketProductName(marketName)) {
            throw InvalidMarketNameException()
        } else if (isInvalidId(ownerId)) {
            throw InvalidOwnerIdException()
        } else {
            if (authRepository.isValidOwner(ownerId!!)) {
                repository.createMarket(marketName!!, ownerId)
            } else {
                throw InvalidOwnerIdException()
            }
        }
    }

}