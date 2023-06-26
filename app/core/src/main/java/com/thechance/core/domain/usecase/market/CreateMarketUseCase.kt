package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class CreateMarketUseCase(private val repository: HoneyMartRepository, private val authRepository: AuthRepository) :
    KoinComponent {

    suspend operator fun invoke(marketName: String?, ownerId: Long?): Boolean {
        isValidInput(marketName, ownerId)?.let { throw it }
        return if (authRepository.isValidOwner(ownerId!!)) {
            repository.createMarket(marketName = marketName!!, ownerId = ownerId)
        } else {
            throw InvalidOwnerIdException()
        }
    }

    private fun isValidInput(
        marketName: String?, ownerId: Long?
    ): Exception? {
        return if (!isValidMarketProductName(marketName)) {
            InvalidMarketNameException()
        } else if (isInvalidId(ownerId)) {
            InvalidOwnerIdException()
        } else {
            null
        }
    }

}