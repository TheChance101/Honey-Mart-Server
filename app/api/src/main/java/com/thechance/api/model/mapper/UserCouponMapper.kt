package com.thechance.api.model.mapper

import com.thechance.api.model.coupon.UserCouponModel
import com.thechance.core.entity.coupon.UserCoupon

fun UserCoupon.toApiUserCoupon(): UserCouponModel {
    return UserCouponModel(
        couponId = couponId,
        count = count,
        discountPercentage = discountPercentage,
        expirationDate = expirationDate.toString(),
        product = product.toApiProductModel(),
        isClipped = isClipped,
        isUsed = isUsed
    )
}