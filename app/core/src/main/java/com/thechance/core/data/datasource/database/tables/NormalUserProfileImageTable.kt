package com.thechance.core.data.datasource.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object NormalUserProfileImageTable : LongIdTable() {
    val imageUrl = text("imageUrl")
    val userId = reference(" userId", NormalUserTable.id)
}