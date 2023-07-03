package com.thechance.core.domain.usecase.user

import com.thechance.core.data.security.token.Tokens
import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import com.thechance.core.utils.NORMAL_USER_ROLE
import org.koin.core.component.KoinComponent

class VerifyUserUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(email: String, password: String): Tokens {
        return if (repository.isEmailExists(email)) {
            validateUser(email, password)
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

}