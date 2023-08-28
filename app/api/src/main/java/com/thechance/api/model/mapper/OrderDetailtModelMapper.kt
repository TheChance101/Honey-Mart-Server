package com.thechance.api.model.mapper

import com.thechance.api.model.order.OrderDetailsModel
import com.thechance.api.utils.convertDateToMillis
import com.thechance.core.entity.order.OrderDetails

internal fun OrderDetails.toApiMarketOrder(): OrderDetailsModel {
    return OrderDetailsModel(
        orderId = orderId,
        userId = userId,
        marketId = marketId,
        products = products.map { it.toApiProductWithCount() },
        totalPrice = totalPrice,
        date = date.convertDateToMillis(),
        state = state
    )
}