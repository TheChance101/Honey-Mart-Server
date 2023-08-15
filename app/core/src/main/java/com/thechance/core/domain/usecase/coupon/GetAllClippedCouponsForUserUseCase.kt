package com.thechance.core.domain.usecase.coupon

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.coupon.UserCoupon
import com.thechance.core.utils.InvalidUserIdException
import com.thechance.core.utils.NORMAL_USER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class GetAllClippedCouponsForUserUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(userId: Long?, role: String?): List<UserCoupon> {

        return if (isInvalidId(userId) || !isValidRole(NORMAL_USER_ROLE, role)) {
            throw InvalidUserIdException()
        } else {
            repository.getClippedCouponsForUser(userId!!)
        }
    }
}