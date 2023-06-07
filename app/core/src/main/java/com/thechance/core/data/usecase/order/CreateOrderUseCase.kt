package com.thechance.core.data.usecase.order

import com.thechance.core.data.model.Order
import com.thechance.core.data.model.OrderItem
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import org.koin.core.component.KoinComponent

class CreateOrderUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        marketId: Long?,
        orderDate: String,
        totalPrice: Double,
        isPaid: Boolean,
        products: List<OrderItem>?
    ): Order {
        isValidInput(marketId!!, orderDate, totalPrice, products!!)?.let { throw it }
        val isMarketDeleted = repository.isMarketDeleted(marketId)
        return if (isMarketDeleted == null) {
            throw IdNotFoundException()
        } else if (isMarketDeleted) {
            throw MarketDeletedException()
        } else {
            repository.createOrder(marketId, orderDate, totalPrice, isPaid, products)
        }
    }

    private fun isValidInput(
        marketId: Long,
        orderDate: String,
        totalPrice: Double,
        products: List<OrderItem>
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