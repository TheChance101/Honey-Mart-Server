package com.thechance.core.data.datasource.database.tables.coupon

import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object CouponTable : LongIdTable() {
    val productId = reference("productId", ProductTable)
    val marketId = reference("marketId", MarketTable)
    val count = integer("count")
    val discountPercentage = double("discountPercentage")
    val expirationDate = datetime("expirationDate")
}