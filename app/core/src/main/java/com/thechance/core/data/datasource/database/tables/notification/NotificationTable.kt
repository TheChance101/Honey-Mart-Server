package com.thechance.core.data.datasource.database.tables.notification

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.timestamp

object NotificationTable: LongIdTable() {
    val title = text("title")
    val body = text("body")
    val timeStamp = timestamp("timeStamp")
    val userId = reference("userId",NormalUserTable)
}