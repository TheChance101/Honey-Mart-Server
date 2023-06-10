package com.thechance.api.mapper

import com.thechance.api.model.ProductInCartModel
import com.thechance.core.entity.ProductInCart


internal fun ProductInCart.toApiProductInCartModel(): ProductInCartModel {
    return ProductInCartModel(id = id, name = name, count = count, price = price)
}