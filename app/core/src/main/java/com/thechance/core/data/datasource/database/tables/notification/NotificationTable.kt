package com.thechance.core.data.datasource.database.tables.notification

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object NotificationTable : LongIdTable() {
    val title = text("title")
    val body = text("body")
    val timeStamp = datetime("timeStamp").clientDefault { LocalDateTime.now() }
    val receiverId = reference("userId", NormalUserTable)
}
