package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.coupon.CouponTable
import com.thechance.core.entity.Product
import com.thechance.core.entity.coupon.UserCoupon
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toUserCoupon(product: Product, isClipped: Boolean): UserCoupon {
    return UserCoupon(
        couponId = this[CouponTable.id].value,
        count = this[CouponTable.count],
        discountPercentage = this[CouponTable.discountPercentage],
        expirationDate = this[CouponTable.expirationDate],
        product = product,
        isClipped = isClipped,
        isUsed = false
    )
}