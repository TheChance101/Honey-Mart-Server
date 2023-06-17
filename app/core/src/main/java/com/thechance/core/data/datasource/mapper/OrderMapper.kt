package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.data.datasource.database.tables.order.OrderProductTable
import com.thechance.core.entity.ProductInCart
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toProductInOrder(): ProductInCart {
    return ProductInCart(
        id = this[ProductTable.id].value,
        name = this[ProductTable.name],
        count = this[OrderProductTable.count],
        price = this[ProductTable.price]
    )
}

