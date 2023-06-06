package com.thechance.core.data.usecase.product

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import com.thechance.core.data.utils.isInvalidId
import org.koin.core.component.KoinComponent

class UpdateProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): Boolean {
        isValidInput(productId, productName, productPrice, productQuantity)?.let { throw it }

        val isProductDeleted = repository.isProductDeleted(productId!!)

        return if (isProductDeleted == null) {
            throw IdNotFoundException()
        } else if (isProductDeleted) {
            throw ProductDeletedException()
        } else {
            repository.updateProduct(productId, productName, productPrice, productQuantity)
        }

    }


    private fun isValidInput(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): Exception? {
        return if (isInvalidId(productId)) {
            InvalidProductIdException()
        } else if (productName == null && productPrice == null && productQuantity.isNullOrEmpty()) {
            InvalidInputException()
        } else if (productName != null && !isValidNameLength(productName)) {
            InvalidProductNameException()
        } else if (productQuantity != null && !isValidNameLength(productQuantity)) {
            InvalidProductQuantityException()
        } else if (productPrice != null && InvalidPrice(productPrice)) {
            InvalidProductPriceException()
        } else {
            null
        }
    }
}