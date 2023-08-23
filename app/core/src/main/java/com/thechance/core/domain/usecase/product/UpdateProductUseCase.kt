package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class UpdateProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(
        productId: Long?, productName: String?, productPrice: Double?, description: String?, marketOwnerId: Long?,
        role: String?
    ): Boolean {
        isValidInput(productId, productName, productPrice, description, marketOwnerId, role)?.let { throw it }

        val isProductDeleted = repository.isProductDeleted(productId!!)

        return if (isProductDeleted == null) {
            throw IdNotFoundException()
        } else if (isProductDeleted) {
            throw ProductDeletedException()
        } else if (!isMarketOwner(marketOwnerId!!, productId)) {
            throw UnauthorizedException()
        } else {
            repository.updateProduct(productId, productName, productPrice, description)
        }

    }

    private fun isValidInput(
        productId: Long?, productName: String?, productPrice: Double?, description: String?, marketOwnerId: Long?,
        role: String?
    ): Exception? {
        return when {
            !isValidMarketProductName(productName) -> {
                InvalidProductNameException()
            }

            isInValidDescription(description) -> {
                InvalidProductDescriptionException()
            }

            isInvalidPrice(productPrice) -> {
                InvalidProductPriceException()
            }

            isInvalidId(productId) -> {
                InvalidProductIdException()
            }

            isInvalidId(marketOwnerId) -> {
                InvalidOwnerIdException()
            }

            !isValidRole(MARKET_OWNER_ROLE, role) -> {
                InvalidOwnerIdException()
            }

            else -> {
                null
            }
        }
    }

    private suspend fun isMarketOwner(marketOwnerId: Long, productId: Long): Boolean {
        val marketId = repository.getProductMarketId(productId)
        return repository.getOwnerIdByMarketId(marketId) == marketOwnerId
    }

}