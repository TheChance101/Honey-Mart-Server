package com.example.ui.service

import com.example.model.Market

interface MarketService {

    suspend fun createMarket(marketName: String): Market

    suspend fun getAllMarkets(): List<Market>

    suspend fun deleteMarket(marketId: Long): Boolean

    suspend fun updateMarket(marketId: Long, marketName: String): Market

    suspend fun isDeleted(marketId: Long): Boolean

}