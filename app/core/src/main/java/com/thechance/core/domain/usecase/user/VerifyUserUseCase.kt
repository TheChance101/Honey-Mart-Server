package com.thechance.core.domain.usecase.user

import com.thechance.core.data.security.token.Tokens
import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.InvalidDeviceTokenException
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import com.thechance.core.utils.NORMAL_USER_ROLE
import org.koin.core.component.KoinComponent

class VerifyUserUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(email: String, password: String, deviceToken: String?): Tokens {
        return if (deviceToken.isNullOrEmpty()) {
            throw InvalidDeviceTokenException()
        } else if (repository.isEmailExists(email)) {
            validateUser(email, password, deviceToken)
        } else {
            throw InvalidUserNameOrPasswordException()
        }
    }

    private suspend fun validateUser(email: String, password: String, deviceToken: String): Tokens {
        return repository.getUserByEmail(email).let { user ->
            if (repository.isUserValidPassword(user, password)) {
                repository.saveUserDeviceTokens(user.userId, deviceToken)
                repository.getTokens(id = user.userId, role = NORMAL_USER_ROLE)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }

}