package com.thechance.core.data.usecase.order

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidMarketIdException
import com.thechance.core.data.utils.InvalidOrderTotalPriceException
import com.thechance.core.data.utils.invalidPrice
import com.thechance.core.data.utils.isInvalidId
import org.koin.core.component.KoinComponent

class CreateOrderUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        userId: Long?
    ): Boolean {
        val isCreated = repository.createOrder(repository.getCartId(userId!!)!!, userId)
        if (isCreated) {
            repository.deleteAllProductsInCart(repository.getCartId(userId)!!)
        }
        return isCreated
    }

    private fun isValidInput(
        marketId: Long,
        totalPrice: Double,
    ): Exception? {
        return when {
            isInvalidId(marketId) -> {
                InvalidMarketIdException()
            }

            invalidPrice(totalPrice) -> {
                InvalidOrderTotalPriceException()
            }

            else -> null
        }
    }
}