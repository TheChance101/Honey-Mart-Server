package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.entity.Image
import com.thechance.core.entity.product.Product
import com.thechance.core.entity.product.ProductWithAverageRating
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toProduct(images: List<Image>): Product {
    return Product(
        id = this[ProductTable.id].value,
        name = this[ProductTable.name].toString(),
        price = this[ProductTable.price],
        description = this[ProductTable.quantity],
        images = images,
        marketId = this[ProductTable.marketId].value
    )
}

internal fun Product.toProductWithAverageRating(averageRating: Float): ProductWithAverageRating {
    return ProductWithAverageRating(
        id = id,
        name = name,
        description = description,
        price = price,
        images = images,
        marketId = marketId,
        averageRating = averageRating
    )
}
