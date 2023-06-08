package com.thechance.core.data.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object CategoriesTable : LongIdTable() {
    val name = text("name")
    val isDeleted = bool("isDeleted")
    val marketId = reference("marketId", MarketTable)
    val imageId = integer("imageId")
}