package com.thechance.api.model.mapper

import com.thechance.api.model.order.MarketOrderModel
import com.thechance.api.utils.convertDateToMillis
import com.thechance.core.entity.order.MarketOrder

internal fun MarketOrder.toApiMarketOrder(): MarketOrderModel {
    return MarketOrderModel(
        orderId = orderId,
        totalPrice = totalPrice,
        state = state,
        date = date.convertDateToMillis(),
        user = user.toApiUserModel()
    )
}

internal fun List<MarketOrder>.toApiMarketOrders() = map { it.toApiMarketOrder() }


