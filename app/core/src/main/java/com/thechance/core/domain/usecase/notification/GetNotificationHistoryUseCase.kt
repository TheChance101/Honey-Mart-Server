package com.thechance.core.domain.usecase.notification

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.Notification
import com.thechance.core.utils.*
import com.thechance.core.utils.NORMAL_USER_ROLE
import com.thechance.core.utils.ORDER_STATUS_CANCELED_BY_OWNER
import com.thechance.core.utils.ORDER_STATUS_PENDING
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class GetNotificationHistoryUseCase(private val repository: AuthRepository) : KoinComponent {
    suspend operator fun invoke(receiverId: Long?, role: String?): List<Notification> {
        return if (isInvalidId(receiverId) || !isValidRole(NORMAL_USER_ROLE, role)) {
            throw InvalidUserIdException()
        } else {
            repository.getNotificationHistory(receiverId!!)
        }
    }
}