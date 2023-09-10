package com.thechance.core.domain.usecase.coupon

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.product.Product
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class SearchMarketProductsWithoutValidCouponsUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(ownerId: Long?, role: String?, productName: String?): List<Product> {

        val marketId = repository.getMarketIdByOwnerId(ownerId = ownerId!!)

        return when {
            isInvalidId(ownerId) || !isValidRole(MARKET_OWNER_ROLE, role) -> {
                throw InvalidOwnerIdException()
            }

            productName.isNullOrEmpty() -> {
                throw MissingQueryParameterException()
            }

            !isValidQuery(productName) -> {
                throw InvalidProductNameException()
            }

            marketId == null -> {
                throw InvalidMarketIdException()
            }

            else -> {
                repository.searchMarketProductsWithoutValidCoupons(marketId, productName)
            }
        }
    }
}