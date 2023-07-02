package com.thechance.core.data.datasource.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object MarketTable : LongIdTable() {
    val name = text("name")
    val ownerId = reference("ownerId", OwnerTable)
    val isDeleted = bool("isDeleted").default(false)
    val imageUrl = text("imageUrl").default("")
    val address = text("address").default("")
    val latitude = double("latitude").default(0.0)
    val longitude = double("longitude").default(0.0)
    val description = text("description").default("")
}