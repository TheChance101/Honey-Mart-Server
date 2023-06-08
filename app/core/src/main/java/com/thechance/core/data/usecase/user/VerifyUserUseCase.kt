package com.thechance.core.data.usecase.user

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidUserNameOrPasswordException
import org.koin.core.component.KoinComponent

class VerifyUserUseCase(
    private val repository: HoneyMartRepository,
) : KoinComponent {

    suspend operator fun invoke(name: String, password: String): String {
        val token = repository.validateUser(name, password)
        return token.ifEmpty {
            throw InvalidUserNameOrPasswordException()
        }
    }


}