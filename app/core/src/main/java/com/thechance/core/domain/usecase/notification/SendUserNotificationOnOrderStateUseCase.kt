package com.thechance.core.domain.usecase.notification

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.NotificationRequest
import com.thechance.core.utils.ORDER_STATUS_CANCELED_BY_OWNER
import com.thechance.core.utils.ORDER_STATUS_DONE
import com.thechance.core.utils.ORDER_STATUS_IN_PROGRESS
import org.koin.core.component.KoinComponent

class SendUserNotificationOnOrderStateUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(receiverId: Long, orderId: Long, orderState: Int): Boolean {
        val pairForOrderState = orderStateContentMap[orderState]
        return if (pairForOrderState != null) {
            val (title, body) = pairForOrderState
            val tokens = repository.getDeviceTokens(receiverId)
            val notification = NotificationRequest(tokens,orderId,title,body,orderState)
            return repository.sendNotification(notification).also {
                repository.saveNotification(title, body, receiverId,orderId)
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
        private const val ORDER_IN_PROGRESS_TITLE = "Order in progress!"
        private const val ORDER_IN_PROGRESS_BODY =
            "Your order is being prepared, you will be updated soon and will receive your order with in the mentioned days"
        private const val ORDER_CANCELLED_TITLE = "Order got cancelled!"
        private const val ORDER_CANCELLED_BODY =
            "We regret to inform you that your recent order has been canceled. We apologize for any inconvenience this may have caused. "
        private const val ORDER_DONE_TITLE = "Order Is Complete!"
        private const val ORDER_DONE_BODY =
            "Thank you for your order! We're delighted to confirm that your purchase has been successfully completed."
    }
}