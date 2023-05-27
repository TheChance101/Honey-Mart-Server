package com.the_chance.data.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object CategoriesTable : LongIdTable() {
    val name = text("name")
    val image = text("image")
    val isDeleted = bool("isDeleted")
    val marketId = reference("marketId", MarketTable)
}