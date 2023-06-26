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
        return if (isInvalidId(productId)) {
            InvalidProductIdException()
        } else if (productName == null && productPrice == null && description.isNullOrEmpty()) {
            InvalidInputException()
        } else if (productName != null && !isValidMarketProductName(productName)) {
            InvalidProductNameException()
        } else if (description != null && !isInValidDescription(description)) {
            InvalidProductDescriptionException()
        } else if (productPrice != null && isInvalidPrice(productPrice)) {
            InvalidProductPriceException()
        } else if (isInvalidId(marketOwnerId) || !isValidRole(MARKET_OWNER_ROLE, role)) {
            InvalidOwnerIdException()
        } else {
            null
        }
    }

    private suspend fun isMarketOwner(marketOwnerId: Long, productId: Long): Boolean {
        val marketId = repository.getProductMarketId(productId)
        return repository.getOwnerIdByMarketId(marketId) == marketOwnerId
    }

}