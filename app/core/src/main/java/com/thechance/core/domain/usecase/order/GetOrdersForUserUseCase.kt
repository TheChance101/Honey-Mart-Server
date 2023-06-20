package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Order
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class GetOrdersForUserUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(userId: Long?, role: String?, state: Int?): List<Order> {
        return if (isInvalidId(userId) || !isValidRole(NORMAL_USER_ROLE, role)) {
            throw InvalidUserIdException()
        } else if (state in ORDER_STATE_IN_PROGRESS..ORDER_STATE_CANCELED && state != null) {
            repository.getOrdersForUser(userId!!, state)
        } else {
            repository.getAllOrdersForUser(userId!!)
        }
    }
}