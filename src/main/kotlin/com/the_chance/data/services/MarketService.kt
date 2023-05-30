package com.the_chance.data.services

import com.the_chance.data.models.Market
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.data.tables.MarketTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.util.regex.Pattern

interface MarketService {

    suspend fun createMarket(marketName: String): Market

    suspend fun getAllMarkets(): List<Market>

    suspend fun deleteMarket(marketId: Long): Boolean

    suspend fun updateMarket(marketId: Long, marketName: String): Market

    suspend fun isDeleted(marketId: Long): Boolean

}