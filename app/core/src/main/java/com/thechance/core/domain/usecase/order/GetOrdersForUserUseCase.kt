package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Order
import com.thechance.core.utils.InvalidUserIdException
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetOrdersForUserUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(userId: Long?): List<Order> {
        return if (isInvalidId(userId)) {
            throw InvalidUserIdException()
        } else {
            repository.getAllOrdersForUser(userId!!)
        }
    }
}