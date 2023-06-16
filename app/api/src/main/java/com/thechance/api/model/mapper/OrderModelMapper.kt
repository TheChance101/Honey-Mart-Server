package com.thechance.api.model.mapper

import com.thechance.api.model.OrderModel
import com.thechance.api.utils.convertDateToMillis
import com.thechance.core.entity.Order

internal fun Order.toApiOrderModel(): OrderModel {
    return OrderModel(
        orderId = orderId,
        totalPrice = totalPrice,
        state = state,
        date = date.convertDateToMillis()
    )
}

internal fun List<Order>.toApiOrders() = map { it.toApiOrderModel() }


