package com.thechance.core.data.usecase.cart

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidProductIdException
import com.thechance.core.data.utils.InvalidUserIdException
import com.thechance.core.data.utils.CountInvalidInputException
import com.thechance.core.data.utils.isInvalidId
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
            //check productId in the Product table with the counts
            // check before insert if it in cart just change the count.
            repository.addToCart(userId!!, productId!!, count)
        }
    }


}