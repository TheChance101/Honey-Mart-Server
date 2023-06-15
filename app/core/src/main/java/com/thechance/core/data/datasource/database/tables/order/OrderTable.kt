package com.thechance.core.data.datasource.database.tables.order

import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.datasource.database.tables.NormalUserTable
import com.thechance.core.utils.ORDER_STATE_IN_PROGRESS
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object OrderTable : LongIdTable() {
    val userId = reference("userId", NormalUserTable)
    val marketId = reference("marketId", MarketTable)
    val orderDate = datetime("orderDate").clientDefault { LocalDateTime.now() }
    val totalPrice = double("totalPrice")
    val state = integer("state").default(ORDER_STATE_IN_PROGRESS)
}