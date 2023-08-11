package com.thechance.core.data.repository.dataSource

interface NotificationDataSource {
    suspend fun sendNotificationByTokens(tokens: List<String>, orderId: Long, title: String, body: String): Boolean
}