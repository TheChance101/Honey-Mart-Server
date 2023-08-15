package com.thechance.core.data.datasource

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.thechance.core.data.datasource.database.tables.notification.NotificationHistoryTable
import com.thechance.core.data.datasource.mapper.toNotification
import com.thechance.core.data.repository.dataSource.NotificationDataSource
import com.thechance.core.entity.Notification
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class NotificationDataSourceImp(private val firebaseMessaging: FirebaseMessaging) : NotificationDataSource,
    KoinComponent {

    override suspend fun sendNotification(
        tokens: List<String>, orderId: Long, title: String, body: String
    ): Boolean {
        return firebaseMessaging.sendAll(tokens.map {
            Message.builder()
                .putData(TITLE, title)
                .putData(BODY, body)
                .putData(ORDER_ID, orderId.toString())
                .setToken(it)
                .build()
        }).failureCount == 0
    }

    override suspend fun saveNotification(title: String, body: String, receiverId: Long, orderId: Long): Boolean {
        return dbQuery {
            NotificationHistoryTable.insert {
                it[this.title] = title
                it[this.body] = body
                it[this.receiverId] = receiverId
                it[this.orderId] = orderId
            }
            true
        }
    }

    override suspend fun getNotificationHistory(receiverId: Long): List<Notification> = dbQuery {
        NotificationHistoryTable.select { NotificationHistoryTable.receiverId eq receiverId }.map {
            it.toNotification()
        }.toList()
    }

    companion object {
        private const val TITLE = "title"
        private const val BODY = "body"
        private const val ORDER_ID = "orderId"
    }
}