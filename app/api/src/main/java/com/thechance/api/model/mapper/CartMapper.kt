package com.thechance.api.model.mapper

import com.thechance.api.model.CartModel
import com.thechance.core.entity.Cart


internal fun Cart.toApiCart(): CartModel {
    return CartModel(products = products.map { it.toApiProductInCartModel() }, total = this.total)
}