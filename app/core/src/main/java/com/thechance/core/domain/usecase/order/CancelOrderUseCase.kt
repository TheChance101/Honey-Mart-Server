package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.InvalidOrderIdException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class CancelOrderUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(orderId: Long?): Boolean {
        validateInputs(orderId)
        if (repository.isOrderExist(orderId!!)) {
            return repository.cancelOrder(orderId)
        } else {
            throw InvalidOrderIdException()
        }
    }

    private fun validateInputs(
        orderId: Long?
    ): Exception? {
        return when {
            isInvalidId(orderId) -> {
                InvalidOrderIdException()
            }

            else -> null
        }
    }
}