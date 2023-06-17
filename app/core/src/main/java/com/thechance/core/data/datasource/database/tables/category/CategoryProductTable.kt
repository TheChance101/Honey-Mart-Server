package com.thechance.core.data.datasource.database.tables.category

import com.thechance.core.data.datasource.database.tables.product.ProductTable
import org.jetbrains.exposed.sql.Table

object CategoryProductTable : Table() {
    val productId = reference("productId", ProductTable)
    val categoryId = reference("categoryId", CategoriesTable)
}