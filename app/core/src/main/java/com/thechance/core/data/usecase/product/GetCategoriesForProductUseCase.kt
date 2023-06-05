package com.thechance.core.data.usecase.product

import com.thechance.core.data.model.Category
import com.thechance.core.data.service.ProductService
import com.thechance.core.data.utils.InvalidProductIdException
import org.koin.core.component.KoinComponent

class GetCategoriesForProductUseCase(private val productService: ProductService) : KoinComponent {

    suspend operator fun invoke(productId: Long?): List<Category> {
        return if (checkId(productId)) {
            throw InvalidProductIdException()
        } else {
            productService.getAllCategoryForProduct(productId)
        }
    }

    private fun checkId(id: Long?): Boolean {
        return id == null
    }
}