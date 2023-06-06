package com.thechance.core.data.usecase.product

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import com.thechance.core.data.utils.isValidId
import com.thechance.core.data.utils.isValidIds
import org.koin.core.component.KoinComponent

class UpdateProductCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(productId: Long?, categoryIds: List<Long>): Boolean {
        return if (isValidId(productId)) {
            throw InvalidProductIdException()
        } else if (isValidIds(categoryIds)) {
            throw InvalidCategoryIdException()
        } else if (repository.isProductDeleted(productId!!)) {
            throw ProductDeletedException()
        } else {
            repository.updateProductCategory(productId, categoryIds)
        }
    }
}