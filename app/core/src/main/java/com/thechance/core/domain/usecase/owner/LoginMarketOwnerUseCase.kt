package com.thechance.core.domain.usecase.owner

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import com.thechance.core.utils.MARKET_OWNER_ROLE
import org.koin.core.component.KoinComponent

class LoginMarketOwnerUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(email: String, password: String): String {
        return if (repository.isOwnerEmailExists(email)) {
            validateUser(email, password)
        } else {
            throw InvalidUserNameOrPasswordException()
        }
    }

    private suspend fun validateUser(userName: String, password: String): String {
        return repository.getMarketOwnerByUsername(userName).let { owner ->
            if (repository.isOwnerValidPassword(owner, password)) {
                repository.getToken(id = owner.ownerId, role = MARKET_OWNER_ROLE)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }

}