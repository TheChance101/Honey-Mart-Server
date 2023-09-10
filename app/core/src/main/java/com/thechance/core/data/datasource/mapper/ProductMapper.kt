package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.entity.Image
import com.thechance.core.entity.product.Product
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toProduct(images: List<Image>): Product {
    return Product(
        id = this[ProductTable.id].value,
        name = this[ProductTable.name].toString(),
        price = this[ProductTable.price],
        quantity = this[ProductTable.quantity],
        image = images,
        marketId = this[ProductTable.marketId].value
    )
}
