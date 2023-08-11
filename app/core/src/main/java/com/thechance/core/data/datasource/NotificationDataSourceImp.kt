package com.thechance.core.data.datasource

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.thechance.core.data.repository.dataSource.NotificationDataSource
import org.koin.core.component.KoinComponent

class NotificationDataSourceImp(private val firebaseMessaging: FirebaseMessaging) : NotificationDataSource,
    KoinComponent {
    override suspend fun sendNotificationByTokens(
        userTokens: List<String>,
        title: String,
        body: String
    ): List<String> {
        val response = mutableListOf<String>()
        for (token in userTokens) {
            val message = Message.builder()
                .putData(TITLE, title)
                .putData(BODY, body)
                .setToken(token)
                .build()
            try {
                val result = firebaseMessaging.send(message)
                response.add("Success: $result")
            } catch (e: Exception) {
                response.add(e.message.toString())
            }
        }
        return response
    }

    companion object {
        private const val TITLE = "title"
        private const val BODY = "body"
    }

}