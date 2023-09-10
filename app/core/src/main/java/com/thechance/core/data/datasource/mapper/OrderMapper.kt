package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.order.OrderProductTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.entity.Image
import com.thechance.core.entity.product.ProductInCart
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toProductInOrder(images: List<Image>): ProductInCart {
    return ProductInCart(
        id = this[ProductTable.id].value,
        name = this[ProductTable.name],
        count = this[OrderProductTable.count],
        price = this[ProductTable.price],
        images = images
    )
}

