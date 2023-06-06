package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.mapper.toCategory
import com.thechance.core.data.datasource.mapper.toMarket
import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Market
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class MarketDataSourceImp : MarketDataSource, KoinComponent {
    override suspend fun createMarket(marketName: String): Market =
        dbQuery {
            val newMarket = MarketTable.insert {
                it[name] = marketName
                it[isDeleted] = false
            }
            Market(marketId = newMarket[MarketTable.id].value, marketName = newMarket[MarketTable.name])
        }

    override suspend fun getAllMarkets(): List<Market> = dbQuery {
        MarketTable.select { MarketTable.isDeleted eq false }.map { it.toMarket() }
    }

    override suspend fun getCategoriesByMarket(marketId: Long): List<Category> = dbQuery {
        CategoriesTable.select {
            (CategoriesTable.marketId eq marketId) and (CategoriesTable.isDeleted eq false)
        }.map { it.toCategory() }
    }

    override suspend fun deleteMarket(marketId: Long): Boolean = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) {
            it[isDeleted] = true
        }
        true
    }


    override suspend fun updateMarket(marketId: Long, marketName: String): Market = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) {
            it[name] = marketName
        }
        Market(marketId = marketId, marketName = marketName)
    }

    override suspend fun isDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: false
    }

}