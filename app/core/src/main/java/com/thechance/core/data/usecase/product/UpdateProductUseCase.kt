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
        return when {
            isInvalidId(productId) -> {
                InvalidProductIdException()
            }
            productName == null && productPrice == null && productQuantity.isNullOrEmpty() -> {
                InvalidInputException()
            }
            productName != null && !isValidNameLength(productName) -> {
                InvalidProductNameException()
            }
            productQuantity != null && !isValidNameLength(productQuantity) -> {
                InvalidProductQuantityException()
            }
            productPrice != null && invalidPrice(productPrice) -> {
                InvalidProductPriceException()
            }
            else -> {
                null
            }
        }
    }
}