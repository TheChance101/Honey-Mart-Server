package com.thechance.core.domain.usecase.notification

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.InvalidOrderIdException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class UpdateUserNotificationState(private val repository: AuthRepository) : KoinComponent {
    suspend operator fun invoke(receiverId: Long?, isRead: Boolean?): Boolean {
        return if (isInvalidId(receiverId) || isRead == null) {
            throw InvalidOrderIdException()
        } else {
            repository.updateUserNotificationState(receiverId!!, isRead)
        }
    }
}
