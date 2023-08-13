package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.Notification

interface NotificationDataSource {
    suspend fun sendNotification(tokens: List<String>, orderId: Long, title: String, body: String): Boolean
    suspend fun saveNotification(title: String, body: String, receiverId: Long,orderId: Long): Boolean
    suspend fun getNotificationHistory(receiverId: Long): List<Notification>
}