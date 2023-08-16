package com.thechance.core.data.datasource.database.tables.coupon

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import org.jetbrains.exposed.sql.Table

object CouponUserTable : Table() {
    val couponId = reference("couponId", CouponTable)
    val userId = reference("userId", NormalUserTable)
    val isUsed = bool("isUsed").default(false)
}