package com.thechance.core.data.usecase.owner

import com.thechance.core.data.model.Owner
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import com.thechance.core.data.utils.checkName
import org.koin.core.component.KoinComponent

class CreateOwnerUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(ownerName: String?, password: String?): Owner {
        isValidInput(ownerName, password)?.let { throw it }
        return if (repository.isOwnerNameExists(ownerName!!)) {
            throw UserInvalidException()
        } else {
            repository.createOwner(ownerName, password!!)
        }
    }


    private fun isValidInput(ownerName: String?, password: String?): Exception? {
        return when {
            checkName(ownerName) -> {
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