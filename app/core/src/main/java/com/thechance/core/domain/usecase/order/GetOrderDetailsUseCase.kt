package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.order.OrderDetails
import com.thechance.core.utils.InvalidOrderIdException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetOrderDetailsUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(orderId: Long?): OrderDetails {
        return if (isInvalidId(orderId) || !repository.isOrderExist(orderId!!)) {
            throw InvalidOrderIdException()
        } else {
            repository.getOrderById(orderId)
        }
    }
}