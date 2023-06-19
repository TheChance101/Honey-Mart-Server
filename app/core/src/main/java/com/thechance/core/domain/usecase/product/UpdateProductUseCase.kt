package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class UpdateProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?, marketOwnerId: Long?,
        role: String?
    ): Boolean {
        isValidInput(productId, productName, productPrice, productQuantity, marketOwnerId, role)?.let { throw it }

        val isProductDeleted = repository.isProductDeleted(productId!!)

        return if (isProductDeleted == null) {
            throw IdNotFoundException()
        } else if (isProductDeleted) {
            throw ProductDeletedException()
        } else if (!isMarketOwner(marketOwnerId!!, productId)) {
            throw UnauthorizedException()
        } else {
            repository.updateProduct(productId, productName, productPrice, productQuantity)
        }

    }

    private fun isValidInput(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?, marketOwnerId: Long?,
        role: String?
    ): Exception? {
        return if (isInvalidId(productId)) {
            InvalidProductIdException()
        } else if (productName == null && productPrice == null && productQuantity.isNullOrEmpty()) {
            InvalidInputException()
        } else if (productName != null && !isValidMarketProductName(productName)) {
            InvalidProductNameException()
        } else if (productQuantity != null && !isValidNameLength(productQuantity)) {
            InvalidProductQuantityException()
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