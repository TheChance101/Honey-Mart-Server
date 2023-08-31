package com.thechance.core.domain.usecase.admin

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.Admin
import com.thechance.core.utils.ADMIN_ROLE
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import org.koin.core.component.KoinComponent

class VerifyAdminUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(email: String, password: String): Admin {
        return validateAdmin(email, password)
    }

    private suspend fun validateAdmin(email: String, password: String): Admin {
        return repository.getAdminByEmail(email).let { admin ->
            if (repository.isValidAdmin(password, email)) {
                val tokens = repository.getTokens(id = admin.adminId, role = ADMIN_ROLE)
                Admin(tokens, admin.fullName)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }
}