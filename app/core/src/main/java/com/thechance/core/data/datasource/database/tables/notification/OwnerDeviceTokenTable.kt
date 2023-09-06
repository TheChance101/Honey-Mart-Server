package com.thechance.core.data.datasource.database.tables.notification

import com.thechance.core.data.datasource.database.tables.OwnerTable
import org.jetbrains.exposed.dao.id.LongIdTable

object OwnerDeviceTokenTable : LongIdTable() {
    val token = text("token")
    val ownerId = reference("receiverId", OwnerTable)
}
