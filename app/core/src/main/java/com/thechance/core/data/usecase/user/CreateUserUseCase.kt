package com.thechance.core.data.usecase.user

import com.thechance.core.data.repository.AuthRepository
import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent

class CreateUserUseCase(
    private val repository: AuthRepository,
) : KoinComponent {

    suspend operator fun invoke(userName: String?, password: String?, fullName: String?, email: String?): Boolean {

        isValidInput(userName, password, fullName, email)?.let { throw it }

        return if (repository.isUserNameExists(userName!!)) {
            throw UsernameAlreadyExistException()
        } else if (repository.isEmailExists(email!!)) {
            throw EmailAlreadyExistException()
        } else if (!repository.createUser(userName, password!!, fullName!!, email)) {
            throw UnKnownUserException()
        } else {
            true
        }
    }


    private fun isValidInput(userName: String?, password: String?, fullName: String?, email: String?): Exception? {
        return when {
            !isValidUsername(userName) -> {
                InvalidUserNameInputException()
            }

            !isValidPassword(password) -> {
                InvalidPasswordInputException()
            }

            !isValidEmail(email) -> {
                InvalidEmailException()
            }

            !isValidFullName(fullName) -> {
                InvalidNameException()
            }

            else -> {
                null
            }
        }
    }

}