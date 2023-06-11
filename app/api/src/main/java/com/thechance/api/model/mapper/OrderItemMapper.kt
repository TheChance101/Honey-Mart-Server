package com.thechance.api.model.mapper

import com.thechance.api.model.OrderItemModel
import com.thechance.core.entity.OrderItem

internal fun OrderItem.toApiOrderItemModel(): OrderItemModel {
    return OrderItemModel(
        productId = productId,
        count = count,
        marketId = marketId
    )
}