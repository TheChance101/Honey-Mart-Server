package com.thechance.core.domain.usecase.user

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class CreateUserUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(email: String?, password: String?, fullName: String?): Boolean {

        isValidInput(password, fullName, email)?.let { throw it }

        return if (repository.isEmailExists(email!!)) {
            throw EmailAlreadyExistException()
        } else if (!repository.createUser(password = password!!, fullName = fullName!!, email = email)) {
            throw UnKnownUserException()
        } else {
            true
        }
    }


    private fun isValidInput(password: String?, fullName: String?, email: String?): Exception? {
        return when {

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