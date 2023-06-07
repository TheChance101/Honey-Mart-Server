package com.thechance.core.data.usecase.order

import com.thechance.core.data.repository.HoneyMartRepository
import org.koin.core.component.KoinComponent

class CancelOrderUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(orderId: Long?) {
        repository.cancelOrder(orderId!!)
    }
}