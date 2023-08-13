package com.thechance.core.data.datasource

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.thechance.core.data.datasource.database.tables.notification.NotificationTable
import com.thechance.core.data.repository.dataSource.NotificationDataSource
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.insert
import org.koin.core.component.KoinComponent

class NotificationDataSourceImp(private val firebaseMessaging: FirebaseMessaging) : NotificationDataSource,
    KoinComponent {

    override suspend fun sendNotificationByTokens(
        tokens: List<String>, orderId: Long, title: String, body: String
    ): Boolean {
        val response = mutableListOf<String>()
        for (token in tokens) {
            val message = Message.builder()
                .putData(TITLE, title)
                .putData(BODY, body)
                .putData(ORDER_ID, orderId.toString())
                .setToken(token)
                .build()
            try {
                val result = firebaseMessaging.send(message)
                response.add("Success: $result")
            } catch (e: Exception) {
                response.add(e.message.toString())
            }
        }
        return response.isNotEmpty()
    }

    override suspend fun saveNotification(title: String, body: String, receiverId: Long): Boolean {
        return dbQuery {
            NotificationTable.insert {
                it[this.title] = title
                it[this.body] = body
                it[this.receiverId] = receiverId
            }
            true
        }
    }

    companion object {
        private const val TITLE = "title"
        private const val BODY = "body"
        private const val ORDER_ID = "orderId"
    }
}