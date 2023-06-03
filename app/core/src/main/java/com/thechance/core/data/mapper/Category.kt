package com.thechance.core.data.mapper

import com.thechance.core.data.model.Category
import com.thechance.core.data.model.ProductWithCategory
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

internal fun ResultRow.toProductWithCategory(categories: List<Category>): ProductWithCategory {
    return ProductWithCategory(
        id = this[ProductTable.id].value,
        name = this[ProductTable.name],
        price = this[ProductTable.price],
        quantity = this[ProductTable.quantity],
        category = categories
    )
}