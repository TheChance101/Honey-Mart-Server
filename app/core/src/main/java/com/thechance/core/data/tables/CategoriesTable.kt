package com.thechance.core.data.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object CategoriesTable : LongIdTable() {
    val name = text("name").nullable()
    val isDeleted = bool("isDeleted")
    val marketId = reference("marketId", MarketTable).nullable()
    val imageId = integer("imageId").nullable()
}