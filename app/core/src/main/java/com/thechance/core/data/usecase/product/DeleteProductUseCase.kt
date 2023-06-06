package com.thechance.core.data.usecase.product

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidProductIdException
import com.thechance.core.data.utils.isValidId
import org.koin.core.component.KoinComponent

class DeleteProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(productId: Long?): Boolean {
        return if (isValidId(productId)) {
            throw InvalidProductIdException()
        } else {
            repository.deleteProduct(productId!!)
        }
    }
}