package com.the_chance.data.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object ProductTable : UUIDTable() {
    val name = text("name")
    val price = double("price")
}