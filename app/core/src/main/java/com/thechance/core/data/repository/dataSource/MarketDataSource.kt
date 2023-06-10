package com.thechance.core.data.repository.dataSource

import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Market
import com.thechance.core.data.model.Product

interface MarketDataSource {

    suspend fun createMarket(marketName: String): Market
    suspend fun getAllMarkets(): List<Market>
    suspend fun getCategoriesByMarket(marketId: Long): List<Category>
    suspend fun deleteMarket(marketId: Long): Boolean
    suspend fun updateMarket(marketId: Long, marketName: String): Market
    suspend fun isDeleted(marketId: Long): Boolean?
    suspend fun getMarketId(productId: Long): Long?
}