package com.thechance.api.model.mapper

import com.thechance.api.model.OrderModel
import com.thechance.core.entity.Order

internal fun Order.toApiOrderModel(): OrderModel {
    return OrderModel(
        orderId = orderId,
        totalPrice = totalPrice
    )
}