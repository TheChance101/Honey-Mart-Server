package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.Notification
import com.thechance.core.entity.NotificationRequest

interface NotificationDataSource {
    suspend fun sendNotification(notification: NotificationRequest): Boolean
    suspend fun saveNotification(title: String, body: String, receiverId: Long,orderId: Long): Boolean
    suspend fun getUserNotificationHistory(userId: Long): List<Notification>
    suspend fun getOwnerNotificationHistory(ownerId: Long): List<Notification>
    suspend fun updateNotificationState(receiverId: Long, isRead: Boolean): Boolean
}