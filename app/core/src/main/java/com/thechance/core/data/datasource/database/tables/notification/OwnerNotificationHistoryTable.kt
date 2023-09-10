package com.thechance.core.data.datasource.database.tables.notification

import com.thechance.core.data.datasource.database.tables.OwnerTable
import com.thechance.core.data.datasource.database.tables.order.OrderTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object OwnerNotificationHistoryTable : LongIdTable() {
    val title = text("title")
    val body = text("body")
    val timeStamp = datetime("timeStamp").clientDefault { LocalDateTime.now() }
    val ownerId = reference("ownerId", OwnerTable)
    val orderId = reference("orderId", OrderTable)
    val isRead = bool("isRead").default(false)
}