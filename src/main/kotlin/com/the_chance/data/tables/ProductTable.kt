package com.the_chance.data.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object ProductTable : LongIdTable() {
    val name = text("name")
    val price = double("price")
    val quantity = text("quantity")
    val isDeleted = bool("isDeleted")
}