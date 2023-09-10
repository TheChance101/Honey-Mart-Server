package com.thechance.core.data.datasource.database.tables

import com.thechance.core.data.datasource.database.tables.order.OrderTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object ProductReviewTable : LongIdTable() {
    val userId = reference("userId", NormalUserTable)
    val productId = reference("productId", ProductTable)
    val orderId = reference("orderId", OrderTable)
    val reviewDate = datetime("reviewDate").clientDefault { LocalDateTime.now() }
    val content = text("content")
    val rating = integer("rating").default(0)
}