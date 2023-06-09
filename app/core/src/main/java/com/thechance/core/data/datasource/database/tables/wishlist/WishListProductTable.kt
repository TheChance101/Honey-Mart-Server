package com.thechance.core.data.datasource.database.tables.wishlist

import com.thechance.core.data.datasource.database.tables.product.ProductTable
import org.jetbrains.exposed.sql.Table

object WishListProductTable : Table() {
    val wishListId = reference("wishListId", WishListTable)
    val productId = reference("productId", ProductTable)
    val isDeleted = bool("isDeleted").default(false)

}