package com.thechance.api.model.mapper

import com.thechance.api.model.ProductModel
import com.thechance.core.entity.Product


internal fun Product.toApiProductModel(): ProductModel {
    return ProductModel(id = id, name = name, quantity = quantity, price = price)
}