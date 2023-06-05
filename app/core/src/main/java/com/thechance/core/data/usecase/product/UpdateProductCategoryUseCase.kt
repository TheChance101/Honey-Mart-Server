package com.thechance.core.data.usecase.product

import com.thechance.core.data.service.ProductService
import com.thechance.core.data.utils.InvalidCategoryIdException
import com.thechance.core.data.utils.InvalidProductIdException
import com.thechance.core.data.utils.checkId
import com.thechance.core.data.utils.isValidIds
import org.koin.core.component.KoinComponent

class UpdateProductCategoryUseCase(private val productService: ProductService) : KoinComponent {

    suspend operator fun invoke(productId: Long?, categoryIds: List<Long>): Boolean {
        return if (checkId(productId)) {
            throw InvalidProductIdException()
        } else if (isValidIds(categoryIds)) {
            throw InvalidCategoryIdException()
        } else {
            productService.updateProductCategory(productId, categoryIds)
        }
    }
}