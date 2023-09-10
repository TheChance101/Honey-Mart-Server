package com.thechance.core.domain.usecase.product


import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.product.Product
import com.thechance.core.utils.InvalidProductNameException
import com.thechance.core.utils.MissingQueryParameterException
import com.thechance.core.utils.isValidQuery
import org.koin.core.component.KoinComponent

class SearchProductsByNameUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(productName: String?, sortOrder: String?, page: Int): List<Product> {
        if (productName.isNullOrEmpty()) {
            throw MissingQueryParameterException()
        }
        if (!isValidQuery(productName)) {
            throw InvalidProductNameException()
        }
        return repository.searchProductsByName(productName, sortOrder, page)
    }
}
