package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.*

interface MarketDataSource {
    suspend fun getMarketIdByOwnerId(ownerId: Long): Long?
    suspend fun createMarket(marketName: String): Market
    suspend fun getAllMarkets(): List<Market>
    suspend fun getCategoriesByMarket(marketId: Long): List<Category>
    suspend fun deleteMarket(marketId: Long): Boolean
    suspend fun updateMarket(marketId: Long, marketName: String): Market
    suspend fun isDeleted(marketId: Long): Boolean?
    suspend fun getMarketId(productId: Long): Long?
}