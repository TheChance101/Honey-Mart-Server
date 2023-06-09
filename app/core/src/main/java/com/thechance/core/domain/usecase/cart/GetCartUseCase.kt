package com.thechance.core.domain.usecase.cart

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Cart
import com.thechance.core.utils.InvalidUserIdException
import com.thechance.core.utils.NORMAL_USER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class GetCartUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long?, role: String?): Cart {
        return if (isInvalidId(userId) || !isValidRole(NORMAL_USER_ROLE, role)) {
            throw InvalidUserIdException()
        } else {
            repository.getCart(getCartId(userId!!))
        }
    }

    private suspend fun getCartId(userId: Long): Long {
        return repository.getCartId(userId) ?: repository.createCart(userId)
    }

}