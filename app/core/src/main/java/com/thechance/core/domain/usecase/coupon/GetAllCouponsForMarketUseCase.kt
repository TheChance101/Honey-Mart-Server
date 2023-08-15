package com.thechance.core.domain.usecase.coupon

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.coupon.MarketCoupon
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class GetAllCouponsForMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(ownerId: Long?, role: String?): List<MarketCoupon> {

        val marketId = repository.getMarketIdByOwnerId(ownerId = ownerId!!)

        return if (isInvalidId(ownerId) || !isValidRole(MARKET_OWNER_ROLE, role)) {
            throw InvalidOwnerIdException()
        } else if (marketId == null) {
            throw InvalidMarketIdException()
        } else {
            repository.getCouponsForMarket(marketId)
        }
    }
}