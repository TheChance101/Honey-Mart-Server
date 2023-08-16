package com.thechance.core.domain.usecase.admin

import com.thechance.core.data.security.token.Tokens
import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.ADMIN_ROLE
import com.thechance.core.utils.InvalidUserNameOrPasswordException
import org.koin.core.component.KoinComponent

class VerifyAdminUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(email: String, password: String): Tokens {
        return validateAdmin(email, password)
    }

    private suspend fun validateAdmin(email: String, password: String): Tokens {
        return repository.getAdminByEmail(email).let { admin ->
            if (repository.isValidAdmin(password, email)) {
                repository.getTokens(id = admin.adminId, role = ADMIN_ROLE)
            } else {
                throw InvalidUserNameOrPasswordException()
            }
        }
    }

}