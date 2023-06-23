package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.*
import com.thechance.core.entity.market.Market

interface MarketDataSource {
    suspend fun getMarketIdByOwnerId(ownerId: Long): Long?

    suspend fun createMarket(marketName: String, ownerId: Long): Boolean

    suspend fun getAllMarkets(): List<Market>

    suspend fun getCategoriesByMarket(marketId: Long): List<Category>

    suspend fun deleteMarket(marketId: Long): Boolean

    suspend fun updateMarket(marketId: Long, marketName: String?, imageUrl: String?): Boolean

    suspend fun isDeleted(marketId: Long): Boolean?

    suspend fun getMarketId(productId: Long): Long?

    suspend fun getOwnerIdByMarketId(marketId: Long): Long?

    suspend fun addMarketImage(marketId: Long, imageUrl: String): Boolean

    suspend fun getMarket(marketId: Long): Market?

}