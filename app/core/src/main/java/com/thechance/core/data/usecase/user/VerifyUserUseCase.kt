package com.thechance.core.data.usecase.user

import com.thechance.core.data.repository.AuthRepository
import com.thechance.core.data.utils.UnKnownUserException
import org.koin.core.component.KoinComponent

class VerifyUserUseCase(
    private val repository: AuthRepository,
) : KoinComponent {

    suspend operator fun invoke(name: String, password: String): String {
        val token = repository.validateUser(name, password)
        return token.ifEmpty {
            throw UnKnownUserException()
        }
    }


}