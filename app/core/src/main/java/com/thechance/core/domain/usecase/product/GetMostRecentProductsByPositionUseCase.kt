package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Product
import org.koin.core.component.KoinComponent

class GetMostRecentProductsUseCase(private val repository: HoneyMartRepository) :
    KoinComponent {
    suspend operator fun invoke(page:Int): List<Product> {
        return repository.getMostRecentProducts(page)
    }


}