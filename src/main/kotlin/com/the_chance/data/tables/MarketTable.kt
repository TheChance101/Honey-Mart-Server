package com.the_chance.data.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object MarketTable : IntIdTable() {
    val name = text("name")
    val isDeleted = bool("isDeleted").default(false)
}