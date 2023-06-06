package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Category
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidMarketIdException
import com.thechance.core.data.utils.isValidId
import org.koin.core.component.KoinComponent

class GetCategoriesByMarketIdUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(marketId: Long?): List<Category> {
        return if (isValidId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            repository.getCategoriesByMarket(marketId!!)
        }
    }
}