package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Product
import org.koin.core.component.KoinComponent

class GetMostRecentProductsByPositionUseCase(private val repository: HoneyMartRepository) :
    KoinComponent {
    suspend operator fun invoke(): List<Product> {
        return repository.getMostRecentProductsByPosition()
    }


}