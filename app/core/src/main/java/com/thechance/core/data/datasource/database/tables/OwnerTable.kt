package com.thechance.core.data.datasource.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object OwnerTable : LongIdTable() {
    val email = text("email")
    val fullName = text("fullName")
    val password = OwnerTable.text("password")
    val salt = text("UserSalt")
    val isDeleted = OwnerTable.bool("isDeleted").default(false)
}
