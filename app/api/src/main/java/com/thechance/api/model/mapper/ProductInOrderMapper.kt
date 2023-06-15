package com.thechance.api.model.mapper

import com.thechance.api.model.ProductInOrderModel
import com.thechance.core.entity.ProductInOrder

internal fun ProductInOrder.toApiProductInOrder(): ProductInOrderModel {
    return ProductInOrderModel(
        id = id,
        name = name,
        count = count,
        price = price
    )
}
