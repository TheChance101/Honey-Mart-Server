package com.thechance.core.data.datasource

//class NotificationDataSourceImp(private val firebaseMessaging: FirebaseMessaging) : NotificationDataSource,
//    KoinComponent {
//
//    override suspend fun sendNotification(
//        notification: NotificationRequest
//    ): Boolean {
//        return firebaseMessaging.sendAll(notification.tokens.map {
//            Message.builder()
//                .putData(TITLE, notification.title)
//                .putData(BODY, notification.body)
//                .putData(ORDER_ID, notification.orderId.toString())
//                .putData(ORDER_Status, notification.orderStatus.toString()).setToken(it).build()
//        }).failureCount == 0
//    }
//
//    override suspend fun saveNotification(title: String, body: String, receiverId: Long, orderId: Long): Boolean {
//        return dbQuery {
//            NotificationHistoryTable.insert {
//                it[this.title] = title
//                it[this.body] = body
//                it[this.receiverId] = receiverId
//                it[this.orderId] = orderId
//            }
//            true
//        }
//    }
//
//    override suspend fun getNotificationHistory(receiverId: Long): List<Notification> = dbQuery {
//        NotificationHistoryTable.select { NotificationHistoryTable.receiverId eq receiverId }.map {
//            it.toNotification()
//        }.toList()
//    }
//
//    override suspend fun updateNotificationState(receiverId: Long, isRead: Boolean): Boolean = dbQuery {
//        NotificationHistoryTable.update({ NotificationHistoryTable.receiverId eq receiverId }) { notificationRow ->
//            notificationRow[this.isRead] = isRead
//        }
//        true
//    }
//
//    companion object {
//        private const val TITLE = "title"
//        private const val BODY = "body"
//        private const val ORDER_ID = "orderId"
//        private const val ORDER_Status = "orderId"
//    }
//}