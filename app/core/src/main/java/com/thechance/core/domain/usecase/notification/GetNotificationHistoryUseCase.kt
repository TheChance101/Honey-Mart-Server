package com.thechance.core.domain.usecase.notification

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.Notification
import org.koin.core.component.KoinComponent

class GetNotificationHistoryUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(receiverId: Long): List<Notification> {
        return repository.getNotificationHistory(receiverId)
    }
}