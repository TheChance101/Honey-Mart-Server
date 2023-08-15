package com.thechance.core.domain.usecase.coupon

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.coupon.Coupon
import org.koin.core.component.KoinComponent

class GetValidCouponsUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(): List<Coupon> {
        return repository.getAllValidCoupons()
    }
}