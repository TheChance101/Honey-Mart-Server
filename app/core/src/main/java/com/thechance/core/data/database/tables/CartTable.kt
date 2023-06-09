package com.thechance.core.data.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object CartTable : LongIdTable() {
    val userId = reference("userId", NormalUserTable)
}