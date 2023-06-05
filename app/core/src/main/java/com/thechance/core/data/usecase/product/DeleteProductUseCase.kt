package com.thechance.core.data.usecase.product

import com.thechance.core.data.service.ProductService
import com.thechance.core.data.utils.InvalidProductIdException
import com.thechance.core.data.utils.checkId
import org.koin.core.component.KoinComponent

class DeleteProductUseCase(private val productService: ProductService) : KoinComponent {

    suspend operator fun invoke(productId: Long?): Boolean {
        return if (checkId(productId)) {
            throw InvalidProductIdException()
        } else {
            productService.deleteProduct(productId)
        }
    }
}