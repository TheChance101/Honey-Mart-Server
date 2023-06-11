package com.thechance.core.data.datasource.database.tables.wishlist

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import org.jetbrains.exposed.dao.id.LongIdTable

object WishListTable : LongIdTable() {
    val userId = reference("userId", NormalUserTable)
}
