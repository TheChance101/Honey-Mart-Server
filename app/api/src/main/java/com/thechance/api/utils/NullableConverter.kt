package com.thechance.api.utils

import com.thechance.api.model.CreateOrderRequest
import com.thechance.core.data.model.OrderItem

fun Double?.orZero() = this ?: 0.0


fun String?.toLongIds() = this?.split(",")?.map { it.toLongOrNull() ?: 0L } ?: emptyList()

/*
internal fun List<CreateOrderRequest.OrderItemRequest>?.toOrderItems(): List<OrderItem> {
    return this?.map {
        OrderItem(
            it.productId.toLong(),
            it.quantity.toInt()
        )
    } ?: emptyList()
}*/
