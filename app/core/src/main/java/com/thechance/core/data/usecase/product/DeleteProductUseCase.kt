package com.thechance.core.data.usecase.product

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidProductIdException
import com.thechance.core.data.utils.ProductDeletedException
import com.thechance.core.data.utils.isInvalidId
import org.koin.core.component.KoinComponent

class DeleteProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(productId: Long?): Boolean {
        return if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else {
            val isProductDeleted = repository.isProductDeleted(productId!!)
            if (isProductDeleted == null) {
                throw IdNotFoundException()
            } else if (isProductDeleted) {
                throw ProductDeletedException()
            } else {
                repository.deleteProduct(productId)
            }
        }
    }
}