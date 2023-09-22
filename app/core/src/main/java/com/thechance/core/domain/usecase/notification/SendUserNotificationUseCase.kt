package com.thechance.core.domain.usecase.notification

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.NotificationRequest
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class SendUserNotificationUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long, orderId: Long, orderState: Int): Boolean {
        val pairForOrderState = orderStateContentMap[orderState]
        return if (pairForOrderState != null) {
            val (title, body) = pairForOrderState
            val deviceToken = repository.getUserDeviceTokens(userId)
            if (deviceToken.isEmpty() || deviceToken.first().isEmpty()) {
                return true
            }
            val titleWithOrderId = title.replace(ORDER_ID, "$orderId")
            val notification = NotificationRequest(deviceToken, orderId, titleWithOrderId, body, orderState)
            return repository.sendNotification(notification).also {
                repository.saveUserNotification(titleWithOrderId, body, userId, orderId)
            }
        } else {
            false
        }
    }

    private val orderStateContentMap = mapOf(
        ORDER_STATUS_IN_PROGRESS to Pair(ORDER_IN_PROGRESS_TITLE, ORDER_IN_PROGRESS_BODY),
        ORDER_STATUS_CANCELED_BY_OWNER to Pair(ORDER_CANCELLED_TITLE, ORDER_CANCELLED_BODY),
        ORDER_STATUS_DONE to Pair(ORDER_DONE_TITLE, ORDER_DONE_BODY),
    )

    companion object {
        private const val ORDER_ID = "orderId"
        private const val ORDER_IN_PROGRESS_TITLE = "Order in progress! #$ORDER_ID"
        private const val ORDER_IN_PROGRESS_BODY =
            "Your order is being prepared, and you will receive it soon."
        private const val ORDER_CANCELLED_TITLE = "Order #$ORDER_ID got cancelled!"
        private const val ORDER_CANCELLED_BODY =
            "We regret to inform you that your order has been canceled. We apologize for any inconvenience this may have caused."
        private const val ORDER_DONE_TITLE = "Order Is Complete! #$ORDER_ID"
        private const val ORDER_DONE_BODY =
            "Thank you for your order! We're delighted to confirm that your purchase has been successfully completed."
    }

}
