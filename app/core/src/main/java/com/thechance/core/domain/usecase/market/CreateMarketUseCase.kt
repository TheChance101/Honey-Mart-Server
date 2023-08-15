package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class CreateMarketUseCase(private val repository: HoneyMartRepository, private val authRepository: AuthRepository) :
    KoinComponent {

    suspend operator fun invoke(
        ownerId: Long?, role: String?,
        name: String?, address: String?, description: String?
    ): Long? {
        isValidInput(ownerId, role, name, address, description)?.let { throw it }
        return if (authRepository.isValidOwner(ownerId!!)) {
            repository.createMarket(ownerId, name!!, address!!, description!!)
        } else {
            throw InvalidOwnerIdException()
        }
    }

    private fun isValidInput(
        ownerId: Long?, role: String?,
        name: String?, address: String?, description: String?
    ): Exception? {
        return when {
            !isValidMarketProductName(name) -> {
                InvalidMarketNameException()
            }

            !isValidAddress(address) -> {
                InvalidInputException()
            }

            isInValidDescription(description) -> {
                InvalidDescriptionException()
            }

            !isValidRole(MARKET_OWNER_ROLE, role) -> {
                InvalidOwnerIdException()
            }

            isInvalidId(ownerId) -> {
                InvalidOwnerIdException()
            }

            else -> {
                null
            }
        }
    }

}