package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.MARKET_OWNER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class DeleteProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(productId: Long?, marketOwnerId: Long?, role: String?): Boolean {
        return if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else if (isInvalidId(marketOwnerId) || !isValidRole(MARKET_OWNER_ROLE, role)) {
            throw InvalidOwnerIdException()
        } else {
            if (isMarketOwner(marketOwnerId!!, productId!!)) {
                val isProductDeleted = repository.isProductDeleted(productId)
                if (isProductDeleted == null) {
                    throw IdNotFoundException()
                } else if (isProductDeleted) {
                    throw ProductDeletedException()
                } else {
                    repository.deleteProduct(productId)
                }
            } else {
                throw UnauthorizedException()
            }
        }
    }

    private suspend fun isMarketOwner(marketOwnerId: Long, productId: Long): Boolean {
        val marketId = repository.getProductMarketId(productId)
        return repository.getOwnerIdByMarketId(marketId) == marketOwnerId
    }
}