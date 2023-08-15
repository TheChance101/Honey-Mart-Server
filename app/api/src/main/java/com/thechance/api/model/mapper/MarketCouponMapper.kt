package com.thechance.api.model.mapper

import com.thechance.api.model.coupon.MarketCouponModel
import com.thechance.core.entity.coupon.MarketCoupon

internal fun MarketCoupon.toApiMarketCoupon(): MarketCouponModel {
    return MarketCouponModel(
        couponId = couponId,
        count = count,
        discountPercentage = discountPercentage,
        expirationDate = expirationDate.toString(),
        product = product.toApiProductModel()
    )
}