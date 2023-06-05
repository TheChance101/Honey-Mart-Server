package com.thechance.core.data.usecase.product

import com.thechance.core.data.service.ProductService
import com.thechance.core.data.utils.InvalidCategoryIdException
import com.thechance.core.data.utils.InvalidProductIdException
import org.koin.core.component.KoinComponent

class UpdateProductCategoryUseCase(private val productService: ProductService) : KoinComponent {

    suspend operator fun invoke(productId: Long?, categoryIds: List<Long>): Boolean {
        return if (checkId(productId)) {
            throw InvalidProductIdException()
        } else if (isValidCategoryIds(categoryIds)) {
            throw InvalidCategoryIdException()
        } else {
            productService.updateProductCategory(productId, categoryIds)
        }
    }

    private fun checkId(id: Long?): Boolean {
        return id == null
    }

    private fun isValidCategoryIds(categoryIds: List<Long>?): Boolean {
        return categoryIds.isNullOrEmpty() || categoryIds.filterNot { it == 0L }.isEmpty()
    }
}