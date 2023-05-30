package com.example.core.data

import com.example.model.Market
import com.example.ui.service.MarketService
import com.example.core.data.tables.CategoriesTable
import com.example.core.data.tables.MarketTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class MarketServiceImp(database: Database) : BaseService(database, MarketTable, CategoriesTable), MarketService,
    KoinComponent {

    override suspend fun createMarket(marketName: String): Market = dbQuery {
        val newMarket = MarketTable.insert {
            it[name] = marketName
            it[isDeleted] = false
        }
        Market(
            marketId = newMarket[MarketTable.id].value,
            marketName = newMarket[MarketTable.name],
        )
    }

    override suspend fun getAllMarkets(): List<Market> = dbQuery {
        MarketTable.select { MarketTable.isDeleted eq false }.map {
            Market(
                marketId = it[MarketTable.id].value,
                marketName = it[MarketTable.name],
            )
        }
    }

    override suspend fun deleteMarket(marketId: Long): Boolean = dbQuery {
        if (isDeleted(marketId)) {
            false
        } else {
            val existingMarket = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
            if (existingMarket != null) {
                MarketTable.update({ MarketTable.id eq marketId }) {
                    it[MarketTable.isDeleted] = true
                }
                true
            } else {
                throw NoSuchElementException("Market with ID $marketId not found.")
            }
        }
    }

    override suspend fun updateMarket(marketId: Long, marketName: String): Market = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) {
            it[name] = marketName
        }
        val updatedMarket = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        if (updatedMarket != null) {
            Market(
                marketId = updatedMarket[MarketTable.id].value,
                marketName = updatedMarket[MarketTable.name]
            )
        } else {
            throw NoSuchElementException("Market with ID $marketId not found.")
        }
    }

    override suspend fun isDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw NoSuchElementException("Market with ID $marketId not found.")
    }

}