package com.thechance.core.data.datasource.mapper

import com.thechance.core.entity.Category
import com.thechance.core.data.datasource.database.tables.category.CategoriesTable
import org.jetbrains.exposed.sql.ResultRow


internal fun ResultRow.toCategory(): Category {
    return Category(
        categoryId = this[CategoriesTable.id].value,
        categoryName = this[CategoriesTable.name].toString(),
        imageId = this[CategoriesTable.imageId]
    )
}