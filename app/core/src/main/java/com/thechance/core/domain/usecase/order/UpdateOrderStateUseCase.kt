package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class UpdateOrderStateUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(orderId: Long?, newOrderState: Int?, role: String?): Boolean {
        return when {
            isInvalidId(orderId) || !repository.isOrderExist(orderId!!) -> {
                throw InvalidOrderIdException()
            }

            newOrderState == null || newOrderState !in ORDER_STATE_IN_PROGRESS..ORDER_STATE_DELETED -> {
                throw InvalidStateOrderException()
            }

            else -> {
                val state = repository.getOrderState(orderId)
                if (isValidRole(NORMAL_USER_ROLE, role)) {
                    validateUser(state, newOrderState)
                } else if (isValidRole(MARKET_OWNER_ROLE, role)) {
                    validateOwner(state, newOrderState)
                } else {
                    throw InvalidUserIdException()
                }
                repository.updateOrderState(orderId, newOrderState)
            }
        }
    }

    private fun validateUser(state: Int, newOrderState: Int) {
        when {
            (state == ORDER_STATE_DONE) && (newOrderState in ORDER_STATE_IN_PROGRESS..ORDER_STATE_CANCELED) -> {
                throw CantUpdateOrderStateException()
            }

            state == ORDER_STATE_DELETED -> {
                throw CantUpdateOrderStateException()
            }

            (state == ORDER_STATE_CANCELED) && (newOrderState != ORDER_STATE_DELETED) -> {
                throw CantUpdateOrderStateException()
            }

            (state == ORDER_STATE_IN_PROGRESS) && (newOrderState != ORDER_STATE_CANCELED) -> {
                throw CantUpdateOrderStateException()
            }
        }
    }

    private fun validateOwner(state: Int, newOrderState: Int) {
        when {
            state == ORDER_STATE_DONE -> {
                throw CantUpdateOrderStateException()
            }

            state == ORDER_STATE_DELETED -> {
                throw CantUpdateOrderStateException()
            }

            state == ORDER_STATE_CANCELED -> {
                throw CantUpdateOrderStateException()
            }

            (state == ORDER_STATE_IN_PROGRESS) && (newOrderState != ORDER_STATE_DONE) -> {
                throw CantUpdateOrderStateException()
            }
        }
    }
}