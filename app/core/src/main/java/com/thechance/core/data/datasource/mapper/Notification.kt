package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.notification.OwnerNotificationHistoryTable
import com.thechance.core.data.datasource.database.tables.notification.UserNotificationHistoryTable
import com.thechance.core.entity.Notification
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toUserNotification(): Notification = Notification(
    id = this[UserNotificationHistoryTable.id].value,
    title = this[UserNotificationHistoryTable.title],
    body = this[UserNotificationHistoryTable.body],
    receiverId = this[UserNotificationHistoryTable.userId].value,
    orderId = this[UserNotificationHistoryTable.orderId].value,
    timeStamp = this[UserNotificationHistoryTable.timeStamp],
)

internal fun ResultRow.toOwnerNotification(): Notification = Notification(
    id = this[OwnerNotificationHistoryTable.id].value,
    title = this[OwnerNotificationHistoryTable.title],
    body = this[OwnerNotificationHistoryTable.body],
    receiverId = this[OwnerNotificationHistoryTable.ownerId].value,
    orderId = this[OwnerNotificationHistoryTable.orderId].value,
    timeStamp = this[OwnerNotificationHistoryTable.timeStamp],
)

