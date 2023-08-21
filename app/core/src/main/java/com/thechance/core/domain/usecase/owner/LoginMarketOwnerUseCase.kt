package com.thechance.core.domain.usecase.owner

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.OwnerTokens
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import com.thechance.core.utils.MARKET_OWNER_ROLE
import org.koin.core.component.KoinComponent

class LoginMarketOwnerUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(email: String, password: String): OwnerTokens {
        return if (repository.isOwnerEmailExists(email)) {
            validateUser(email, password)
        } else {
            throw InvalidUserNameOrPasswordException()
        }
    }

    private suspend fun validateUser(email: String, password: String): OwnerTokens {
        return repository.getMarketOwnerByEmail(email).let { owner ->
            if (repository.isOwnerValidPassword(owner, password)) {
                val marketId = repository.getMarketIdByOwnerId(owner.ownerId)
                val tokens = repository.getTokens(id = owner.ownerId, role = MARKET_OWNER_ROLE)
                OwnerTokens(owner.fullName, marketId, tokens)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }

}
