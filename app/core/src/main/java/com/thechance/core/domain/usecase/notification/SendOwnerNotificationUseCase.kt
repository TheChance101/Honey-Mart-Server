package com.thechance.core.domain.usecase.notification

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.NotificationRequest
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class SendOwnerNotificationUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(ownerId: Long, orderId: Long, orderState: Int): Boolean {
        val pairForOrderState = orderStateContentMap[orderState]
        return if (pairForOrderState != null) {
            val (title, body) = pairForOrderState
            val deviceToken = repository.getOwnerDeviceTokens(ownerId)
            if (deviceToken.isEmpty() || deviceToken.first().isEmpty()) {
                return true
            }
            val notification = NotificationRequest(deviceToken, orderId, title, body, orderState)
            return repository.sendNotification(notification).also {
                repository.saveOwnerNotification(title, body, ownerId, orderId)
            }
        } else {
            false
        }
    }

    private val orderStateContentMap = mapOf(
        ORDER_STATUS_PENDING to Pair(ORDER_STATUS_PENDING_TITLE, ORDER_STATUS_PENDING_BODY),
        ORDER_STATUS_CANCELED_BY_USER to Pair(ORDER_CANCELLED_TITLE, ORDER_CANCELLED_BODY),
    )

    companion object {
        private const val ORDER_STATUS_PENDING_TITLE = "New Order received!"
        private const val ORDER_STATUS_PENDING_BODY =
            "You have received a new order from a customer. Please review and respond as soon as possible."
        private const val ORDER_CANCELLED_TITLE = "Order got cancelled!"
        private const val ORDER_CANCELLED_BODY =
            "We regret to inform you that your recent order has been canceled. We apologize for any inconvenience this may have caused. "
    }
}