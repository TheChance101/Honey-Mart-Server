package com.thechance.core.data.usecase.user

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent

class VerifyUserUseCase(
    private val repository: HoneyMartRepository,
) : KoinComponent {

    suspend operator fun invoke(userId: Long, password: String): String {
        val token = repository.isValidatePassword(userId, password)
        return token.ifEmpty {
            throw InvalidUserNameOrPasswordException()
        }
    }

}