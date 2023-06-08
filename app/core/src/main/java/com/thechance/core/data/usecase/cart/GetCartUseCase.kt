package com.thechance.core.data.usecase.cart

import com.thechance.core.data.model.Cart
import com.thechance.core.data.model.Product
import com.thechance.core.data.model.ProductInCart
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidUserIdException
import com.thechance.core.data.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetCartUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long?): Cart {
        return if (isInvalidId(userId)) {
            throw InvalidUserIdException()
        } else {
            repository.getCart(userId!!)
        }
    }

}