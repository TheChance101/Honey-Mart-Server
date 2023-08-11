package com.thechance.core.data.repository.dataSource

interface NotificationDataSource {
    suspend fun sendNotificationByTokens(userTokens: List<String>, title: String, body: String): List<String>
}