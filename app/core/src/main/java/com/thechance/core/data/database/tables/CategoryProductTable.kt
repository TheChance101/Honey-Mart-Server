package com.thechance.core.data.database.tables

import org.jetbrains.exposed.sql.Table

object CategoryProductTable : Table() {
    val productId = reference("productId", ProductTable)
    val categoryId = reference("categoryId", CategoriesTable)
}