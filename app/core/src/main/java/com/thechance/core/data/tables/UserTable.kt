package com.thechance.core.data.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable : LongIdTable() {
    val userName = text("userName")
    val password = text("password")
//    val isDeleted = bool("isDeleted").default(false)
    val salt = text("UserSalt")
}