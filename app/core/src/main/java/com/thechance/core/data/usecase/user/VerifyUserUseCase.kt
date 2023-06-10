package com.thechance.core.data.usecase.user

import com.thechance.core.data.repository.AuthRepository
import com.thechance.core.data.utils.InvalidUserNameOrPasswordException
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
            if (repository.isValidPassword(user, password)) {
                repository.getToken(user)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }


}