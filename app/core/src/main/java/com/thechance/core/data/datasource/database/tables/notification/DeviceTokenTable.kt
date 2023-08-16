package com.thechance.core.data.datasource.database.tables.notification

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import org.jetbrains.exposed.dao.id.LongIdTable

object DeviceTokenTable:LongIdTable() {
    val token = text("token")
    val receiverId = reference("receiverId",NormalUserTable)
}