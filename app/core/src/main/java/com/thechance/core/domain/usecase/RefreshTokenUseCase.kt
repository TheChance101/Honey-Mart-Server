package com.thechance.core.domain.usecase

import com.thechance.core.data.security.token.Tokens
import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import com.thechance.core.utils.MARKET_OWNER_ROLE
import com.thechance.core.utils.NORMAL_USER_ROLE
import org.koin.core.component.KoinComponent

class RefreshTokenUseCase (private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(email: String, password: String): Tokens {
        return if (repository.isOwnerEmailExists(email)) {
            validateOwnerUser(email, password)
        } else {
            throw InvalidUserNameOrPasswordException()
        }
    }
    private suspend fun validateUser(userName: String, password: String): Tokens {
        return repository.getUserByEmail(userName).let { user ->
            if (repository.isUserValidPassword(user, password)) {
                repository.getTokens(id = user.userId, role = NORMAL_USER_ROLE)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }
    private suspend fun validateOwnerUser(userName: String, password: String): Tokens {
        return repository.getMarketOwnerByUsername(userName).let { owner ->
            if (repository.isOwnerValidPassword(owner, password)) {
                repository.getTokens(id = owner.ownerId, role = MARKET_OWNER_ROLE)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }
}