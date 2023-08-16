package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.notification.NotificationHistoryTable
import com.thechance.core.entity.Notification
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toNotification(): Notification = Notification(
    id = this[NotificationHistoryTable.id].value,
    title = this[NotificationHistoryTable.title],
    body = this[NotificationHistoryTable.body],
    receiverId = this[NotificationHistoryTable.receiverId].value,
    orderId = this[NotificationHistoryTable.orderId].value,
    timeStamp = this[NotificationHistoryTable.timeStamp],

    )

