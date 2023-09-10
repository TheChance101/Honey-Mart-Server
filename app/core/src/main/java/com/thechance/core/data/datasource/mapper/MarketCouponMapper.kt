package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.coupon.CouponTable
import com.thechance.core.entity.product.Product
import com.thechance.core.entity.coupon.MarketCoupon
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toMarketCoupon(product: Product): MarketCoupon {
    return MarketCoupon(
        couponId = this[CouponTable.id].value,
        count = this[CouponTable.count],
        discountPercentage = this[CouponTable.discountPercentage],
        expirationDate = this[CouponTable.expirationDate],
        product = product,
    )
}