package com.thechance.core.domain.usecase.cart

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.NORMAL_USER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class DeleteCartUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long?, role: String?): Boolean {
        return if (!isValidRole(NORMAL_USER_ROLE, role)) {
            throw InvalidUserIdException()
        } else if (isInvalidId(userId)) {
            throw InvalidUserIdException()
        } else {
            repository.deleteCart(getCartId(userId!!))
        }
    }

    private suspend fun getCartId(userId: Long): Long {
        return repository.getCartId(userId) ?: repository.createCart(userId)
    }
}