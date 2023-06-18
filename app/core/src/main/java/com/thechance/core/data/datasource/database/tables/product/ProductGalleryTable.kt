package com.thechance.core.data.datasource.database.tables.product

import org.jetbrains.exposed.sql.Table

object ProductGalleryTable : Table() {
    val galleryId = reference("galleryId", GalleryTable)
    val productId = reference("productId", ProductTable)
}