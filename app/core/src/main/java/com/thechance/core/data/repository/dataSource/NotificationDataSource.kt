package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.Notification
import com.thechance.core.entity.NotificationRequest

interface NotificationDataSource {
    suspend fun sendNotification(notification: NotificationRequest): Boolean
    suspend fun saveUserNotification(title: String, body: String, receiverId: Long, orderId: Long): Boolean
    suspend fun saveOwnerNotification(title: String, body: String, receiverId: Long, orderId: Long): Boolean
    suspend fun getUserNotificationHistory(userId: Long): List<Notification>
    suspend fun getOwnerNotificationHistory(ownerId: Long): List<Notification>
    suspend fun updateUserNotificationState(receiverId: Long, isRead: Boolean): Boolean
    suspend fun updateOwnerNotificationState(receiverId: Long, isRead: Boolean): Boolean
}