package com.thechance.api.mapper

import com.thechance.api.model.WishListModel
import com.thechance.core.entity.WishList


internal fun WishList.toApiWishListModel(): WishListModel {
    return WishListModel(
        products = products.map { it.toApiProductInWishListModel() }
    )
}