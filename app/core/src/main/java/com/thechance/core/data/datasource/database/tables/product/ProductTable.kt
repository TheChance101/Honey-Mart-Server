package com.thechance.core.data.datasource.database.tables.product

import org.jetbrains.exposed.dao.id.LongIdTable

object ProductTable : LongIdTable() {
    val name = text("name")
    val price = double("price")
    val quantity = text("quantity").nullable()
    val isDeleted = bool("isDeleted").default(false)
}