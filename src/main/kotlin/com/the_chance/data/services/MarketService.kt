package com.the_chance.data.services

import com.the_chance.data.models.Market
import com.the_chance.data.tables.MarketTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class MarketService(database: Database) : BaseService() {
    init {
        transaction(database) {
            SchemaUtils.create(MarketTable)
        }
    }

    suspend fun createMarket(marketName: String): Market = dbQuery {
        val newMarket = MarketTable.insert {
            it[name] = marketName
        }
        Market(
            id = newMarket[MarketTable.id].value,
            name = newMarket[MarketTable.name]
        )
    }
}