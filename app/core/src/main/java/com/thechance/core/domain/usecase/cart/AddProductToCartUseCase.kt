package com.thechance.core.domain.usecase.cart

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class AddProductToCartUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long?, productId: Long?, count: Int?, role: String?): Boolean {
        isInvalidInput(userId, productId, count, role)?.let { throw it }

        val isProductDeleted = repository.isProductDeleted(productId!!)
        val cartId = getCartId(userId!!)

        return if (isProductDeleted == null) {
            throw IdNotFoundException()
        } else if (isProductDeleted) {
            throw ProductDeletedException()
        } else if (!isProductInSameMarket(productId = productId, cartId = cartId)) {
            throw ProductNotInSameCartMarketException()
        } else {
            if (repository.isProductInCart(userId, productId)) {
                repository.updateProductCountInCart(cartId, productId, count!!)
            } else {
                val marketId = repository.getMarketId(productId)
                if (marketId == null) {
                    throw InvalidProductIdException()
                } else {
                    repository.addToCart(
                        cartId = cartId, marketId = marketId, productId = productId, count = count!!
                    )
                }
            }
        }
    }

    private suspend fun isProductInSameMarket(productId: Long, cartId: Long): Boolean {
        val cartMarketId = repository.getCartMarketId(cartId = cartId)
        val productMarketId = repository.getProductMarketId(productId)
        return if (cartMarketId == null) {
            true
        } else {
            cartMarketId == productMarketId
        }
    }

    private suspend fun getCartId(userId: Long): Long {
        return repository.getCartId(userId) ?: repository.createCart(userId)
    }

    private fun isInvalidInput(userId: Long?, productId: Long?, count: Int?, role: String?): Exception? {
        return if (!isValidRole(NORMAL_USER_ROLE, role)) {
            throw InvalidUserIdException()
        } else if (isInvalidId(userId)) {
            throw InvalidUserIdException()
        } else if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else if (count == null || count !in 1..100) {
            throw CountInvalidInputException()
        } else {
            null
        }
    }
}