package com.thechance.core.domain.usecase

import com.thechance.core.data.security.token.Tokens
import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent
import java.util.*

class RefreshTokenUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(token: String?): Tokens {
        if (token.isNullOrEmpty()) {
            throw InvalidTokenException()
        } else {
            validateTokenExpiration(token)
            validateTokenType(token)
            val id = repository.verifyTokenSubject(token).toLongOrNull()

            return when (repository.verifyTokenRole(token)) {
                NORMAL_USER_ROLE -> {
                    validateUser(id)
                }

                MARKET_OWNER_ROLE -> {
                    validateOwner(id)
                }

                else -> {
                    throw InvalidRuleException()
                }
            }
        }
    }

    private fun validateTokenType(token: String) {
        val tokenType = repository.verifyTokenType(token)
        if (tokenType != REFRESH_TOKEN) {
            throw InvalidTokenTypeException()
        }
    }

    private fun validateTokenExpiration(token: String) {
        val expirationDate = repository.getTokenExpiration(token)
        val currentDate = Date(System.currentTimeMillis())
        if (expirationDate < currentDate) {
            throw TokenExpiredException()
        }
    }

    private suspend fun validateUser(userId: Long?): Tokens {
        return repository.getUser(userId!!).let { user ->
            if (repository.isUserExist(userId)) {
                repository.getTokens(id = user.userId, role = NORMAL_USER_ROLE)
            } else {
                throw InvalidUserIdException()
            }
        }
    }

    private suspend fun validateOwner(ownerId: Long?): Tokens {
        return repository.getOwner(ownerId!!).let { owner ->
            if (repository.isValidOwner(ownerId)) {
                repository.getTokens(id = owner.ownerId, role = MARKET_OWNER_ROLE)
            } else {
                throw InvalidOwnerIdException()
            }
        }
    }
}