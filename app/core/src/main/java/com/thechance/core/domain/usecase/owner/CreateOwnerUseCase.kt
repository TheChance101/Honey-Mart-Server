package com.thechance.core.domain.usecase.owner

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.isValidEmail
import com.thechance.core.utils.isValidPassword
import org.koin.core.component.KoinComponent
import javax.naming.InvalidNameException

class CreateOwnerUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(fullName: String?, email: String?, password: String?): Boolean {
        isValidInput(fullName, email, password)?.let { throw it }

        return if (repository.isOwnerEmailExists(email!!)) {
            throw EmailAlreadyExistException()
        } else {
            repository.createOwner(fullName = fullName!!, email = email, password = password!!)
        }
    }


    private fun isValidInput(fullName: String?, email: String?, password: String?): Exception? {
        return when {

            !isValidFullName(fullName) -> { InvalidNameException() }

            !isValidEmail(email) -> { InvalidEmailException() }

            !isValidPassword(password) -> { InvalidUserNameOrPasswordException() }

            else -> { null }
        }
    }

}