package com.thechance.core.data.datasource.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object NormalUserTable : LongIdTable() {
    val password = text("password")
    val fullName = text("fullName")
    val email = text("email")
    val salt = text("UserSalt")
    val imageUrl = text("imageUrl").default("")
}