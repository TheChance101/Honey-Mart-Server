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
        return if (checkProductName(productName)) {
            throw InvalidProductNameException()
        } else if (checkProductQuantity(productQuantity)) {
            throw InvalidProductQuantityException()
        } else if (checkPrice(productPrice)) {
            throw InvalidProductPriceException()
        } else if (isValidCategoryIds(categoriesId)) {
            throw InvalidCategoryIdException()
        } else {
            productService.create(productName, productPrice, productQuantity, categoriesId)
        }
    }

    private fun checkProductName(productName: String?): Boolean {
        return productName?.let {
            return it.length !in 6..20
        } ?: true
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

    private fun isValidCategoryIds(categoryIds: List<Long>?): Boolean {
        return categoryIds.isNullOrEmpty() || categoryIds.filterNot { it == 0L }.isEmpty()
    }
}