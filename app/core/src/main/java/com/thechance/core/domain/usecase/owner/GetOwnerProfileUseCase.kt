package com.thechance.core.domain.usecase.owner

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.User
import com.thechance.core.utils.InvalidOwnerIdException
import com.thechance.core.utils.MARKET_OWNER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class GetOwnerProfileUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(ownerId: Long?, role: String?): User {
        isValidInput(ownerId, role)?.let { throw it }
        return repository.getOwner(ownerId!!)
    }

    private fun isValidInput(ownerId: Long?, role: String?): Exception? {
        return when {

            isInvalidId(ownerId) -> {
                InvalidOwnerIdException()
            }

            !isValidRole(MARKET_OWNER_ROLE, role) -> {
                InvalidOwnerIdException()
            }

            else -> {
                null
            }
        }
    }
}