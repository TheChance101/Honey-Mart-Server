package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.model.Category
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.ProductTable
import org.jetbrains.exposed.sql.ResultRow


internal fun ResultRow.toCategory(): Category {
    return Category(
        categoryId = this[CategoriesTable.id].value,
        categoryName = this[CategoriesTable.name].toString(),
        imageId = this[CategoriesTable.imageId]
    )
}