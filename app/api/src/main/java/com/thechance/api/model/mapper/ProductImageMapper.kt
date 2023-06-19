package com.thechance.api.model.mapper

import com.thechance.api.model.ProductImage
import com.thechance.core.entity.Image

internal fun Image.toApiProductImage(): ProductImage {
    return ProductImage(id = id, imageUrl = imageUrl)
}