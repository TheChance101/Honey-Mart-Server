package com.thechance.core.domain.usecase

import com.thechance.core.data.security.token.Tokens
import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.*
import io.ktor.server.auth.jwt.*
import org.koin.core.component.KoinComponent

class RefreshTokenUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(principal: JWTPrincipal?): Tokens {
        val id = principal?.payload?.subject?.toLongOrNull()
        val role = principal?.getClaim(ROLE_TYPE, String::class)
        val expireAt = principal?.expiresAt

        return when (role) {
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

    private suspend fun validateUser(userId: Long?): Tokens {
        return repository.getUserByEmail(userName).let { user ->
            if (repository.isOwnerEmailExists(userId)) {
                repository.getTokens(id = user.userId, role = NORMAL_USER_ROLE)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }

    private suspend fun validateOwner(ownerId: Long?): Tokens {
        return repository.getOwner(ownerId!!).let { owner ->
            if (repository.isOwnerValidPassword(owner, password)) {
                repository.getTokens(id = owner.ownerId, role = MARKET_OWNER_ROLE)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }
}