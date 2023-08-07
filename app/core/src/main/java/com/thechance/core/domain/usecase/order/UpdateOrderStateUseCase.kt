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

            newOrderState == null || newOrderState !in ORDER_STATUS_IN_PROGRESS..ORDER_STATUS_DELETED -> {
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
            state == ORDER_STATUS_DELETED -> {
                throw CantUpdateOrderStateException()
            }

            (state == ORDER_STATUS_CANCELED_BY_USER
                    || state == ORDER_STATUS_CANCELED_BY_OWNER
                    || state == ORDER_STATUS_DONE)
                    && (newOrderState != ORDER_STATUS_DELETED) -> {
                throw CantUpdateOrderStateException()
            }

            (state == ORDER_STATUS_IN_PROGRESS
                    || state == ORDER_STATUS_PENDING)
                    && (newOrderState != ORDER_STATUS_CANCELED_BY_USER) -> {
                throw CantUpdateOrderStateException()
            }
        }
    }

    private fun validateOwner(state: Int, newOrderState: Int) {
        when {
            state == ORDER_STATUS_DONE
                    || state == ORDER_STATUS_DELETED
                    || state == ORDER_STATUS_CANCELED_BY_USER
                    || state == ORDER_STATUS_CANCELED_BY_OWNER -> {
                throw CantUpdateOrderStateException()
            }

            state == ORDER_STATUS_IN_PROGRESS
                    && newOrderState != ORDER_STATUS_DONE
                    && newOrderState != ORDER_STATUS_CANCELED_BY_OWNER -> {
                throw CantUpdateOrderStateException()
            }

            state == ORDER_STATUS_PENDING
                    && newOrderState != ORDER_STATUS_IN_PROGRESS
                    && newOrderState != ORDER_STATUS_CANCELED_BY_OWNER -> {
                throw CantUpdateOrderStateException()
            }
        }
    }
}