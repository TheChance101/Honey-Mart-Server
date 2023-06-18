package com.thechance.core.domain.usecase.user

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.User
import com.thechance.core.utils.InvalidUserIdException
import com.thechance.core.utils.NORMAL_USER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class GetUserProfileUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long?, role: String?): User {
        isValidInput(userId, role)?.let { throw it }
        return repository.getProfile(userId!!)
    }


    private fun isValidInput(userId: Long?, role: String?): Exception? {
        return when {

            isInvalidId(userId) -> {
                InvalidUserIdException()
            }

            !isValidRole(NORMAL_USER_ROLE, role) -> {
                InvalidUserIdException()
            }

            else -> {
                null
            }
        }
    }

}