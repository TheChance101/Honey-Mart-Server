package com.thechance.core.data.service

import com.thechance.core.data.datasource.MarketDataSource
import com.thechance.core.data.mapper.toCategory
import com.thechance.core.data.mapper.toMarket
import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Market
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.ItemNotAvailableException
import com.thechance.core.data.validation.market.MarketValidation
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class MarketService(
    private val marketValidationImpl: MarketValidation,
    private val marketDataSource: MarketDataSource
) : BaseService(MarketTable, CategoriesTable),
    KoinComponent {

    suspend fun createMarket(marketName: String?): Market {
        marketValidationImpl.checkMarketName(marketName)?.let { throw it }
        return marketDataSource.createMarket(marketName)
    }

    suspend fun getAllMarkets(): List<Market> = marketDataSource.getAllMarkets()


    suspend fun getCategoriesByMarket(marketId: Long?): List<Category> {
        marketValidationImpl.checkId(marketId)?.let { throw it }
        return if (!marketDataSource.isDeleted(marketId!!)) {
            marketDataSource.getCategoriesByMarket(marketId)
        } else {
            throw IdNotFoundException()
        }
    }

    suspend fun deleteMarket(marketId: Long?): Boolean {
        marketValidationImpl.checkId(marketId)?.let { throw it }
        return marketDataSource.deleteMarket(marketId)
    }


    suspend fun updateMarket(marketId: Long?, marketName: String?): Market {
        marketValidationImpl.checkId(marketId)?.let { throw it }
        marketValidationImpl.checkMarketName(marketName)?.let { throw it }
        return if (marketDataSource.isDeleted(marketId!!)) {
            throw ItemNotAvailableException()
        } else {
            marketDataSource.updateMarket(marketId, marketName!!)
        }
    }


}