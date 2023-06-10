package com.thechance.api.mapper

import com.thechance.api.model.ProductInWishListModel
import com.thechance.core.entity.ProductInWishList

internal fun ProductInWishList.toApiProductInWishListModel(): ProductInWishListModel {
    return ProductInWishListModel(productId = productId, name = name, price = price)
}