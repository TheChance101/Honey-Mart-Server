package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class UpdateProductCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(
        productId: Long?, categoryIds: List<Long>, marketOwnerId: Long?, role: String?
    ): Boolean {
        return if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else if (isValidIds(categoryIds)) {
            throw InvalidCategoryIdException()
        } else if (isInvalidId(marketOwnerId) || !isValidRole(MARKET_OWNER_ROLE, role)) {
            throw InvalidOwnerIdException()
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