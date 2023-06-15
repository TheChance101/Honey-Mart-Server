package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class UpdateOrderStateUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(orderId: Long?, newOrderState: Int?, role: String?): Boolean {

        return when {
            isInvalidId(orderId) || !isValidRole(MARKET_OWNER_ROLE, role) -> {
                throw InvalidUserIdException()
            }

            !repository.isOrderExist(orderId!!) -> {
                throw InvalidOrderIdException()
            }

            newOrderState == null || newOrderState !in ORDER_STATE_IN_PROGRESS..ORDER_STATE_IN_DONE -> {
                throw InvalidStateOrderException()
            }

            else -> {
                repository.updateOrderState(orderId, newOrderState)
            }
        }
    }
}