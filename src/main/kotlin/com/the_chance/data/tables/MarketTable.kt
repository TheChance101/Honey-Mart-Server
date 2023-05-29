package com.the_chance.data.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object MarketTable : LongIdTable() {
    val name = text("name")
    val isDeleted = bool("isDeleted").default(false)
}