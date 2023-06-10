package com.thechance.core.data.datasource.database.tables.order

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object OrderTable : LongIdTable() {
    val orderDate = date("orderDate").default(LocalDate.now())
    val totalPrice = double("totalPrice")
    val isCanceled = bool("isCanceled").default(false)
    val userId = reference("userId", NormalUserTable)
}