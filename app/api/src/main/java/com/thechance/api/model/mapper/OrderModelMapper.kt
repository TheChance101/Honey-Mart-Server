package com.thechance.api.model.mapper

import com.thechance.api.model.OrderModel
import com.thechance.core.entity.Order
import com.thechance.core.entity.OrderDetails

internal fun Order.toApiOrderModel(): OrderModel {
    return OrderModel(
        orderId = orderId,
        totalPrice = totalPrice
    )
}