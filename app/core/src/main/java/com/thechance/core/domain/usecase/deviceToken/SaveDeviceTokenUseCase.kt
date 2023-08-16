package com.thechance.core.domain.usecase.deviceToken

import com.thechance.core.domain.repository.AuthRepository
import org.koin.core.component.KoinComponent

class SaveDeviceTokenUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(receiverId: Long, token: String) {
        repository.saveDeviceTokens(receiverId,token)
    }
}