package com.thechance.core.data.datasource.database.tables.product

import org.jetbrains.exposed.dao.id.LongIdTable

object GalleryTable : LongIdTable() {
    val imageUrl = text("imageUrl")
}