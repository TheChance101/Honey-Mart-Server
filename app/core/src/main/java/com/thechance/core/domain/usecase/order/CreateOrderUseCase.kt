package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class CreateOrderUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        userId: Long?,
        role: String?
    ): Boolean {
        return if (isInvalidId(userId) || !isValidRole(NORMAL_USER_ROLE, role)) {
            throw InvalidUserIdException()
        } else if (!isEmptyCart(getCartId(userId!!))) {
            val isCreated = repository.createOrder(getCartId(userId), userId)
            if (isCreated) {
                repository.deleteAllProductsInCart(getCartId(userId))
            }
            isCreated
        } else {
            throw EmptyCartException()
        }
    }

    private suspend fun getCartId(userId: Long): Long {
        return repository.getCartId(userId) ?: repository.createCart(userId)
    }

    private suspend fun isEmptyCart(cartId: Long): Boolean {
        return repository.getCart(cartId).products.isEmpty()
    }
}