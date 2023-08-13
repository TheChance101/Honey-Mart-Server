package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Notification
import org.koin.core.component.KoinComponent

class GetNotificationHistoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(receiverId: Long): List<Notification> {
        return repository.getNotificationHistory(receiverId)
    }
}