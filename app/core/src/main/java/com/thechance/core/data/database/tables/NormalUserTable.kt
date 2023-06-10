package com.thechance.core.data.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object NormalUserTable : LongIdTable() {
    val userName = text("userName")
    val password = text("password")
    val fullName = text("fullName")
    val email = text("email")
    val salt = text("UserSalt")
}