package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.Category
import com.thechance.core.entity.market.Market

interface MarketDataSource {

    suspend fun getMarketIdByOwnerId(ownerId: Long): Long?
    suspend fun createMarket(ownerId:Long, name: String, address: String, description: String): Long?
    suspend fun getAllMarkets(page: Int): List<Market>
    suspend fun getCategoriesByMarket(marketId: Long): List<Category>
    suspend fun deleteMarket(marketId: Long): Boolean
    suspend fun updateMarket(
        marketId: Long,
        marketName: String? = null,
        imageUrl: String? = null,
        latitude: Double? = null,
        longitude: Double? = null,
        description: String? = null,
        address: String? = null
    ): Boolean

    suspend fun updateMarketStatus(marketId: Long, status: Boolean): Boolean

    suspend fun isDeleted(marketId: Long): Boolean?

    suspend fun getMarketId(productId: Long): Long?

    suspend fun getOwnerIdByMarketId(marketId: Long): Long?

    suspend fun addMarketImage(marketId: Long, imageUrl: String): Boolean

    suspend fun getMarket(marketId: Long): Market?

    suspend fun getProductsCountForMarket(marketId: Long): Int
}