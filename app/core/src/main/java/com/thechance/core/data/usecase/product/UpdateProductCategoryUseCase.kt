package com.thechance.core.data.usecase.product

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import com.thechance.core.data.utils.isInvalidId
import com.thechance.core.data.utils.isValidIds
import org.koin.core.component.KoinComponent

class UpdateProductCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(productId: Long?, categoryIds: List<Long>): Boolean {
        return if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else if (isValidIds(categoryIds)) {
            throw InvalidCategoryIdException()
        } else {
            val isProductDeleted = repository.isProductDeleted(productId!!)
            if (isProductDeleted == null) {
                throw IdNotFoundException()
            } else if (isProductDeleted) {
                throw ProductDeletedException()
            } else {
                repository.updateProductCategory(productId, categoryIds)
            }
        }
    }
}