package com.thechance.core.data.datasource

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.thechance.core.data.datasource.database.tables.notification.OwnerNotificationHistoryTable
import com.thechance.core.data.datasource.database.tables.notification.UserNotificationHistoryTable
import com.thechance.core.data.datasource.mapper.toOwnerNotification
import com.thechance.core.data.datasource.mapper.toUserNotification
import com.thechance.core.data.repository.dataSource.NotificationDataSource
import com.thechance.core.entity.Notification
import com.thechance.core.entity.NotificationRequest
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class NotificationDataSourceImp(private val firebaseMessaging: FirebaseMessaging) : NotificationDataSource,
    KoinComponent {

    override suspend fun sendNotification(
        notification: NotificationRequest
    ): Boolean {
        val response = firebaseMessaging.sendAll(notification.tokens.map {
            Message.builder()
                .setNotification(
                    com.google.firebase.messaging.Notification.builder()
                        .setTitle(notification.title)
                        .setBody(notification.body)
                        .build()
                )
                .setAndroidConfig(
                    AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setNotification(
                            AndroidNotification.builder()
                                .setIcon("stock_ticker_update")
                                .setColor("#FEBC33")
                                .build()
                        )
                        .build()
                )
                .putData(TITLE, notification.title)
                .putData(BODY, notification.body)
                .putData(ORDER_ID, notification.orderId.toString())
                .putData(ORDER_Status, notification.orderStatus.toString())
                .setToken(it)
                .build()
        })
        println(response.responses.map { it.isSuccessful })
        return response.failureCount == 0
    }

    override suspend fun saveUserNotification(title: String, body: String, receiverId: Long, orderId: Long): Boolean {
        return dbQuery {
            UserNotificationHistoryTable.insert {
                it[this.title] = title
                it[this.body] = body
                it[this.userId] = receiverId
                it[this.orderId] = orderId
            }
            true
        }
    }

    override suspend fun saveOwnerNotification(title: String, body: String, receiverId: Long, orderId: Long): Boolean {
        return dbQuery {
            OwnerNotificationHistoryTable.insert {
                it[this.title] = title
                it[this.body] = body
                it[this.ownerId] = receiverId
                it[this.orderId] = orderId
            }
            true
        }
    }

    override suspend fun getUserNotificationHistory(userId: Long): List<Notification> = dbQuery {
        UserNotificationHistoryTable
            .select { UserNotificationHistoryTable.userId eq userId }
            .orderBy(UserNotificationHistoryTable.timeStamp, SortOrder.DESC)
            .map {
                it.toUserNotification()
            }.toList()
    }


    override suspend fun getOwnerNotificationHistory(ownerId: Long): List<Notification> = dbQuery {
        OwnerNotificationHistoryTable
            .select { OwnerNotificationHistoryTable.ownerId eq ownerId }
            .orderBy(OwnerNotificationHistoryTable.timeStamp, SortOrder.DESC)
            .map {
                it.toOwnerNotification()
            }.toList()
    }


    override suspend fun updateUserNotificationState(receiverId: Long, isRead: Boolean): Boolean = dbQuery {
        UserNotificationHistoryTable.update({ UserNotificationHistoryTable.userId eq receiverId }) { notificationRow ->
            notificationRow[this.isRead] = isRead
        }
        true
    }

    override suspend fun updateOwnerNotificationState(receiverId: Long, isRead: Boolean): Boolean = dbQuery {
        OwnerNotificationHistoryTable.update({ OwnerNotificationHistoryTable.ownerId eq receiverId }) { notificationRow ->
            notificationRow[this.isRead] = isRead
        }
        true
    }

    companion object {
        private const val TITLE = "title"
        private const val BODY = "body"
        private const val ORDER_ID = "orderId"
        private const val ORDER_Status = "orderId"
    }
}