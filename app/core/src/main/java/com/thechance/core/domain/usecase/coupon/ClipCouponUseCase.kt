package com.thechance.core.domain.usecase.coupon

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class ClipCouponUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(userId: Long?, role: String?, couponId: Long?): Boolean {

        return when {
            isInvalidId(userId) || !isValidRole(NORMAL_USER_ROLE, role) -> {
                throw InvalidUserIdException()
            }
            isInvalidId(couponId) -> {
                throw InvalidCouponIdException()
            }
            repository.isCouponClipped(couponId!!, userId!!) -> {
                throw CouponAlreadyClippedException()
            }
            !repository.isValidCoupon(couponId) -> {
                throw InvalidCouponException()
            }
            else -> {
                repository.clipCoupon(couponId, userId)
            }
        }
    }
}