package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Product
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class CreateProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        productName: String, productPrice: Double, productQuantity: String?, categoriesId: List<Long>?
    ): Product {
        isValidInput(productName, productPrice, productQuantity, categoriesId)?.let { throw it }

        return if (repository.checkCategoriesInDb(categoriesId!!)) {
            repository.createProduct(productName, productPrice, productQuantity!!, categoriesId!!)
        } else {
            throw NotValidCategoryList()
        }
    }

    private fun isValidInput(
        productName: String, productPrice: Double, productQuantity: String?, categoriesId: List<Long>?
    ): Exception? {
        return when {
            isValidUsername(productName) -> { InvalidProductNameException() }

            checkProductQuantity(productQuantity) -> { InvalidProductQuantityException() }

            isInvalidPrice(productPrice) -> { InvalidProductPriceException() }

            isValidIds(categoriesId) -> { InvalidCategoryIdException() }

            else -> { null }
        }
    }

    private fun checkProductQuantity(quantity: String?): Boolean {
        return quantity?.let {
            return it.length !in 6..20
        } ?: true
    }

}