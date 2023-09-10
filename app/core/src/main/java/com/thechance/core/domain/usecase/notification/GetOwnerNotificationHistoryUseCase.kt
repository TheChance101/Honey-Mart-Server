package com.thechance.core.domain.usecase.notification

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.Notification
import com.thechance.core.utils.InvalidUserIdException
import com.thechance.core.utils.MARKET_OWNER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class GetOwnerNotificationHistoryUseCase(private val repository: AuthRepository) : KoinComponent {
    suspend operator fun invoke(ownerId: Long?, role: String?): List<Notification> {
        return if (isInvalidId(ownerId) || !isValidRole(MARKET_OWNER_ROLE, role)) {
            throw InvalidUserIdException()
        } else {
            repository.getOwnerNotificationHistory(ownerId!!)
        }
    }
}