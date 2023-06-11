package com.thechance.core.domain.usecase.user

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import com.thechance.core.utils.NORMAL_USER_ROLE
import org.koin.core.component.KoinComponent

class VerifyUserUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(userName: String, password: String): String {
        return if (repository.isUserNameExists(userName)) {
            validateUser(userName, password)
        } else {
            throw InvalidUserNameOrPasswordException()
        }
    }

    private suspend fun validateUser(userName: String, password: String): String {
        return repository.getUserByName(userName).let { user ->
            if (repository.isUserValidPassword(user, password)) {
                repository.getToken(id = user.userId, role = NORMAL_USER_ROLE)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }

}