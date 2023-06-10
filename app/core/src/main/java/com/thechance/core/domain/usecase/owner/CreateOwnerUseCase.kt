package com.thechance.core.domain.usecase.owner

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.Owner
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import com.thechance.core.utils.UsernameAlreadyExistException
import com.thechance.core.utils.isValidPassword
import com.thechance.core.utils.isValidUsername
import org.koin.core.component.KoinComponent

class CreateOwnerUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(ownerName: String?, password: String?): Boolean {
        isValidInput(ownerName, password)?.let { throw it }
        return if (repository.isOwnerNameExists(ownerName!!)) {
            throw UsernameAlreadyExistException()
        } else {
            repository.createOwner(ownerName, password!!)
        }
    }


    private fun isValidInput(ownerName: String?, password: String?): Exception? {
        return when {
            isValidUsername(ownerName) -> {
                InvalidUserNameOrPasswordException()
            }

            isValidPassword(password) -> {
                InvalidUserNameOrPasswordException()
            }

            else -> {
                null
            }
        }
    }

}