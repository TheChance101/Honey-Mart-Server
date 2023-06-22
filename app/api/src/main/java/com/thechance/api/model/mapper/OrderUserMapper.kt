package com.thechance.api.model.mapper

import com.thechance.api.model.UserOrderModel
import com.thechance.api.utils.convertDateToMillis
import com.thechance.core.entity.order.UserOrder


internal fun UserOrder.toApiUserOrder(): UserOrderModel {
    return UserOrderModel(
        orderId = orderId,
        totalPrice = totalPrice,
        state = state,
        date = date.convertDateToMillis(),
        market = market.toApiMarketModel(),
        numItems = numItems
    )
}

internal fun List<UserOrder>.toApiUserOrders() = map { it.toApiUserOrder() }