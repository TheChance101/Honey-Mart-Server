package com.example.core.data.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object MarketTable : LongIdTable() {
    val name = text("name")
    val isDeleted = bool("isDeleted").default(false)
}