package com.thechance.core.data.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object CartTable : LongIdTable() {
    val productId = reference("productId", ProductTable)
    val quantity = integer("quantity")
}