package com.thechance.core.data.datasource.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object MarketTable : LongIdTable() {
    val name = text("name")
    val isDeleted = bool("isDeleted").default(false)
}