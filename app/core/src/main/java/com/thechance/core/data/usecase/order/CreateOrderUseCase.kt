package com.thechance.core.data.usecase.order

import com.thechance.core.data.model.Order
import com.thechance.core.data.model.OrderItem
import com.thechance.core.data.repository.HoneyMartRepository
import org.koin.core.component.KoinComponent

class CreateOrderUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        marketId: Long?,
        orderDate: String,
        totalPrice: Double,
        isPaid: Boolean,
        products: List<OrderItem>?
    ): Order {
        return repository.createOrder(marketId!!, orderDate, totalPrice, isPaid, products!!)
    }
}