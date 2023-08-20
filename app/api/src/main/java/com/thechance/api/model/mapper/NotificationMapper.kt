package com.thechance.api.model.mapper

import com.thechance.api.model.NotificationModel
import com.thechance.core.entity.Notification

internal fun Notification.toApiNotification(): NotificationModel {
    return NotificationModel(
        notificationId = id,
        userId = receiverId,
        orderId = this.orderId,
        title = this.title,
        body = this.body,
        date = timeStamp.toString()
    )
}