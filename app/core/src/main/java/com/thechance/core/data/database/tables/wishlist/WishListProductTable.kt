package com.thechance.core.data.database.tables.wishlist

import com.thechance.core.data.database.tables.ProductTable
import com.thechance.core.data.database.tables.wishlist.WishListTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table

object WishListProductTable : Table() {
    val wishListId = reference("wishListId", WishListTable)
    val productId = reference("productId", ProductTable)
    val isDeleted = bool("isDeleted").default(false)

}