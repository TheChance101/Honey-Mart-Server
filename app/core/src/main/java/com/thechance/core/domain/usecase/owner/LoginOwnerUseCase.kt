package com.thechance.core.domain.usecase.owner

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.OwnerTokens
import com.thechance.core.utils.InvalidDeviceTokenException
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import com.thechance.core.utils.MARKET_OWNER_ROLE
import org.koin.core.component.KoinComponent

class LoginOwnerUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(email: String, password: String, deviceToken: String?): OwnerTokens {
        return if (deviceToken.isNullOrEmpty()) {
            throw InvalidDeviceTokenException()
        } else if (repository.isOwnerEmailExists(email)) {
            validateOwner(email, password,deviceToken)
        } else {
            throw InvalidUserNameOrPasswordException()
        }
    }

    private suspend fun validateOwner(email: String, password: String, deviceToken: String): OwnerTokens {
        return repository.getOwnerByEmail(email).let { owner ->
            if (repository.isOwnerValidPassword(owner, password)) {
                val marketId = repository.getMarketIdByOwnerId(owner.ownerId)
                repository.saveOwnerDeviceTokens(owner.ownerId, deviceToken)
                val tokens = repository.getTokens(id = owner.ownerId, role = MARKET_OWNER_ROLE)
                OwnerTokens(owner.fullName, marketId, tokens)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }

}
