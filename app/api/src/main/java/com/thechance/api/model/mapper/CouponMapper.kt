package com.thechance.api.model.mapper

import com.thechance.api.model.coupon.CouponModel
import com.thechance.api.utils.convertDateToMillis
import com.thechance.core.entity.coupon.Coupon

internal fun Coupon.toApiCoupon(): CouponModel {
    return CouponModel(
        couponId = couponId,
        count = count,
        discountPercentage = discountPercentage,
        expirationDate = expirationDate.convertDateToMillis(),
        product = product.toApiProductModel(),
    )
}