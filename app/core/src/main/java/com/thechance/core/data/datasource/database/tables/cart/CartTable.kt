package com.thechance.core.data.datasource.database.tables.cart

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import org.jetbrains.exposed.dao.id.LongIdTable

object CartTable : LongIdTable() {
    val userId = reference("userId", NormalUserTable)
    val marketId = long("marketId").default(0L)
}