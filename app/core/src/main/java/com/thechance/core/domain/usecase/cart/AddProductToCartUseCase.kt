package com.thechance.core.domain.usecase.cart

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class AddProductToCartUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long?, productId: Long?, count: Int?): Boolean {
        return if (isInvalidId(userId)) {
            throw InvalidUserIdException()
        } else if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else if (count == null || count !in 1..100) {
            throw CountInvalidInputException()
        } else {
            val isProductDeleted = repository.isProductDeleted(productId!!)
            if (isProductDeleted == null) {
                throw IdNotFoundException()
            } else if (isProductDeleted) {
                throw ProductDeletedException()
            } else {
                if (repository.isProductInCart(userId!!, productId)) {
                    repository.updateProductCountInCart(userId, productId, count)
                } else {
                    val marketId = repository.getMarketId(productId)
                    if (marketId == null) {
                        throw InvalidProductIdException()
                    } else {
                        repository.addToCart(
                            cartId = getCartId(userId),
                            marketId = marketId,
                            productId = productId,
                            count = count
                        )
                    }
                }
            }
        }
    }

    private suspend fun getCartId(userId: Long): Long {
        return repository.getCartId(userId) ?: repository.createCart(userId)
    }
}