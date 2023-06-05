package com.thechance.core.data.usecase.product

import com.thechance.core.data.model.Product
import com.thechance.core.data.service.ProductService
import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent

class CreateProductUseCase(private val productService: ProductService) : KoinComponent {
    suspend operator fun invoke(
        productName: String,
        productPrice: Double,
        productQuantity: String?,
        categoriesId: List<Long>?
    ): Product {
        return when {
            checkName(productName) -> {
                throw InvalidProductNameException()
            }
            checkProductQuantity(productQuantity) -> {
                throw InvalidProductQuantityException()
            }
            checkPrice(productPrice) -> {
                throw InvalidProductPriceException()
            }
            isValidIds(categoriesId) -> {
                throw InvalidCategoryIdException()
            }
            else -> {
                productService.create(productName, productPrice, productQuantity, categoriesId)
            }
        }
    }

    private fun checkProductQuantity(quantity: String?): Boolean {
        return quantity?.let {
            return it.length !in 6..20
        } ?: true
    }

    private fun checkPrice(price: Double?): Boolean {
        return price?.let {
            return it !in 0.1..999999.0
        } ?: true

    }
}