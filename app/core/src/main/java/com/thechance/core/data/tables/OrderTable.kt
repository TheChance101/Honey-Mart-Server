package com.thechance.core.data.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object OrderTable : LongIdTable() {
    val marketId = long("marketId")
    val orderDate = text("orderDate")
    val totalPrice = double("totalPrice")
    val isPaid = bool("isPaid")
    //TODO: val customerId = reference("userId",UserTable)
}