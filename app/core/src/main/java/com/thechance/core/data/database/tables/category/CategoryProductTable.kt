package com.thechance.core.data.database.tables.category

import com.thechance.core.data.database.tables.ProductTable
import org.jetbrains.exposed.sql.Table

object CategoryProductTable : Table() {
    val productId = reference("productId", ProductTable)
    val categoryId = reference("categoryId", CategoriesTable)
}