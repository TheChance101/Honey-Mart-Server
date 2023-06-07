package com.thechance.core.data.usecase.user

import com.thechance.core.data.model.User
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import com.thechance.core.data.utils.checkName
import org.koin.core.component.KoinComponent

class CreateUserUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(userName: String?, password: String?): User {
        isValidInput(userName, password)?.let { throw it }
        return if (repository.isUserNameExists(userName!!)) {
            throw UserInvalidException()
        } else {
            repository.createUser(userName, password!!)
        }

    }


    private fun isValidInput(userName: String?, password: String?): Exception? {
        return when {
            checkName(userName) -> {
                InvalidUserNameException()
            }

            checkPassword(password) -> {
                InvalidPasswordException()
            }

            else -> {
                null
            }
        }
    }

}