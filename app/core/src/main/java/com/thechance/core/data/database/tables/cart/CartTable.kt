package com.thechance.core.data.database.tables.cart

import com.thechance.core.data.database.tables.NormalUserTable
import org.jetbrains.exposed.dao.id.LongIdTable

object CartTable : LongIdTable() {
    val userId = reference("userId", NormalUserTable)
}