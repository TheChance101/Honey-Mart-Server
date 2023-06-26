package com.thechance.api.model.mapper

import com.thechance.api.model.ProductWithCount
import com.thechance.core.entity.ProductInCart


internal fun ProductInCart.toApiProductWithCount(): ProductWithCount {
    return ProductWithCount(
        id = id,
        name = name,
        count = count,
        price = price,
        images = images.map { it.imageUrl }
    )
}