package com.thechance.core.data.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object WishListTable : LongIdTable() {
    //val productId = reference("productId", ProductTable)
    val userId = reference("userId", UserTable)
}
