package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.ProductTable
import com.thechance.core.data.model.Product
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toProduct(): Product {
    return Product(
        id = this[ProductTable.id].value,
        name = this[ProductTable.name].toString(),
        price = this[ProductTable.price],
        quantity = this[ProductTable.quantity],
    )
}