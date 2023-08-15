package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import org.koin.core.component.KoinComponent

class SaveNotificationUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(title: String, body: String, receiverId: Long, orderId: Long): Boolean {
        return repository.saveNotification(title, body, receiverId, orderId)
    }
}

