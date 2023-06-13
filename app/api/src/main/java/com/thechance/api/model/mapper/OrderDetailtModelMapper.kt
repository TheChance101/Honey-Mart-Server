package com.thechance.api.model.mapper

import com.thechance.api.model.OrderDetailsModel
import com.thechance.core.entity.OrderDetails

internal fun OrderDetails.toApiOrderModel(): OrderDetailsModel {
    return OrderDetailsModel(
        orderId = orderId,
        userId = userId,
        marketId = marketId,
        products = products.map { it.toApiProductInOrder() },
        totalPrice = totalPrice,
        date = date.toString()
    )
}