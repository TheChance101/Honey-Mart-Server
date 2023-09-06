package com.thechance.core.domain.usecase.deviceToken

import com.thechance.core.domain.repository.AuthRepository
import org.koin.core.component.KoinComponent

class SaveUserDeviceTokenUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long, token: String) {
        repository.saveUserDeviceTokens(userId,token)
    }
}