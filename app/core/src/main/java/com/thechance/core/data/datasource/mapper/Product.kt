package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.model.Product
import com.thechance.core.data.tables.ProductTable
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toProduct(): Product {
    return Product(
        id = this[ProductTable.id].value,
        name = this[ProductTable.name].toString(),
        price = this[ProductTable.price],
        quantity = this[ProductTable.quantity],
    )
}