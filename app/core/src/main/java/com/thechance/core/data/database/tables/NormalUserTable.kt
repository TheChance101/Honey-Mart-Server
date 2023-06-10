package com.thechance.core.data.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object NormalUserTable : LongIdTable() {
    val userName = text("userName")
    val password = text("password")
    val salt = text("UserSalt")
}