package com.thechance.core.domain.usecase.cart

import com.thechance.core.data.model.Cart
import com.thechance.core.data.usecase.repository.HoneyMartRepository
import com.thechance.core.utils.InvalidUserIdException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetCartUseCase(private val repository: HoneyMartRepository) :
    KoinComponent {

    suspend operator fun invoke(userId: Long?): Cart {
        return if (isInvalidId(userId)) {
            throw InvalidUserIdException()
        } else {
            repository.getCart(getCartId(userId!!))
        }
    }

    private suspend fun getCartId(userId: Long): Long {
        return repository.getCartId(userId) ?: repository.createCart(userId)
    }
}