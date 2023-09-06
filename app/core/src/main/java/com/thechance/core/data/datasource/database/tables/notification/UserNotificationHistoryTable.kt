package com.thechance.core.data.datasource.database.tables.notification

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import com.thechance.core.data.datasource.database.tables.order.OrderTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserNotificationHistoryTable : LongIdTable() {
    val title = text("title")
    val body = text("body")
    val timeStamp = datetime("timeStamp").clientDefault { LocalDateTime.now() }
    val userId = reference("userId", NormalUserTable)
    val orderId = reference("orderId",OrderTable)
    val isRead = bool("isRead").default(false)
}
