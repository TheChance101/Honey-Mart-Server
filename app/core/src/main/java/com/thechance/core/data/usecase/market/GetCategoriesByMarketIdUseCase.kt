package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Category
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidMarketIdException
import com.thechance.core.data.utils.MarketDeletedException
import com.thechance.core.data.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetCategoriesByMarketIdUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(marketId: Long?): List<Category> {
        return if (isInvalidId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            val isMarketDeleted = repository.isMarketDeleted(marketId!!)
            if (isMarketDeleted == null){
                throw IdNotFoundException()
            }else if (isMarketDeleted){
                throw MarketDeletedException()
            }else{
                repository.getCategoriesByMarket(marketId)
            }
        }
    }
}