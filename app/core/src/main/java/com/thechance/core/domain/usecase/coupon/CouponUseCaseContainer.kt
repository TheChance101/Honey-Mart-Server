package com.thechance.core.domain.usecase.coupon

import org.koin.core.component.KoinComponent

data class CouponUseCaseContainer(
    val createCouponUseCase: CreateCouponUseCase,
    val getValidCouponsUseCase: GetValidCouponsUseCase,
    val getAllCouponsForUserUseCase: GetAllCouponsForUserUseCase
) : KoinComponent