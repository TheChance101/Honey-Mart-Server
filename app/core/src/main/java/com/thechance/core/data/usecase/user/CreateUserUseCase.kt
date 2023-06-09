package com.thechance.core.data.usecase.user

import com.thechance.core.data.repository.AuthRepository
import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent

class CreateUserUseCase(
    private val repository: AuthRepository,
) : KoinComponent {

    suspend operator fun invoke(userName: String?, password: String?): Boolean {
        isValidInput(userName, password)?.let { throw it }
        return if (repository.isUserNameExists(userName!!)) {
            throw UserAlreadyExistException()
        } else if (!repository.createUser(userName, password!!)) {
            throw UnKnownUserException()
        } else {
            true
        }

    }


    private fun isValidInput(userName: String?, password: String?): Exception? {
        return when {
            checkName(userName) -> {
                InvalidUserNameOrPasswordException()
            }

            checkPassword(password) -> {
                InvalidUserNameOrPasswordException()
            }

            else -> {
                null
            }
        }
    }


}