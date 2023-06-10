package com.thechance.core.data.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object WishListProductTable : LongIdTable() {
    val isDeleted = bool("isDeleted").default(false)
    val productId = reference("productId", ProductTable)
    val wishListId = reference("wishListId", WishListTable)

}