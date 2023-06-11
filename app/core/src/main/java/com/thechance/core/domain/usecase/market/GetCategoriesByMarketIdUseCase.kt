package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Category
import com.thechance.core.utils.IdNotFoundException
import com.thechance.core.utils.InvalidMarketIdException
import com.thechance.core.utils.MarketDeletedException
import com.thechance.core.utils.isInvalidId
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