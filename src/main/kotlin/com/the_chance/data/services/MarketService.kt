package com.the_chance.data.services

import com.the_chance.data.models.Market
import com.the_chance.data.tables.MarketTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class MarketService(private val database: Database) : BaseService(database, MarketTable) {

    suspend fun createMarket(marketName: String): Market = dbQuery {
        val newMarket = MarketTable.insert {
            it[name] = marketName
            it[isDeleted] = false
        }
        Market(
            id = newMarket[MarketTable.id].value,
            name = newMarket[MarketTable.name]
        )
    }

    suspend fun getAllMarkets(): List<Market> = dbQuery {
        MarketTable.select { MarketTable.isDeleted eq false }.map {
            Market(
                id = it[MarketTable.id].value,
                name = it[MarketTable.name]
            )
        }
    }


    suspend fun deleteMarket(marketId: Int) = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) {
            it[isDeleted] = true
        }
    }

    suspend fun updateMarket(marketId: Int, marketName: String): Market = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) {
            it[name] = marketName
        }
        val updatedMarket = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        if (updatedMarket != null) {
            Market(
                id = updatedMarket[MarketTable.id].value,
                name = updatedMarket[MarketTable.name]
            )
        } else {
            throw NoSuchElementException("Market with ID $marketId not found.")
        }
    }

}