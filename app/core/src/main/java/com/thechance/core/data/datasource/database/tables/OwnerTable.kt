package com.thechance.core.data.datasource.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object OwnerTable : LongIdTable() {
    val ownerName = OwnerTable.text("ownerName")
    val password = OwnerTable.text("password")
    val salt = text("UserSalt")
    val isDeleted = OwnerTable.bool("isDeleted").default(false)
}
