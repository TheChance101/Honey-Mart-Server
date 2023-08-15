package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.coupon.CouponTable
import com.thechance.core.entity.Product
import com.thechance.core.entity.coupon.Coupon
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toCoupon(product: Product): Coupon {
    return Coupon(
        couponId = this[CouponTable.id].value,
        count = this[CouponTable.count],
        discountPercentage = this[CouponTable.discountPercentage],
        expirationDate = this[CouponTable.expirationDate],
        product = product,
    )
}