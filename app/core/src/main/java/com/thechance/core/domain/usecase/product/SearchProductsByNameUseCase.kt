package com.thechance.core.domain.usecase.product


import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Product
import com.thechance.core.utils.InvalidProductNameException
import org.koin.core.component.KoinComponent

class SearchProductsByNameUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(nameQuery: String, page: Int): List<Product> {
        val products = repository.searchProductsByName(nameQuery, page)
        if (products.isEmpty()) {
            throw InvalidProductNameException()
        }
        return products
    }
}
