package com.the_chance.data.services

import com.the_chance.data.models.Market
import com.the_chance.data.tables.MarketTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.util.regex.Pattern

class MarketService(database: Database) : BaseService(database, MarketTable) {

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

    suspend fun deleteMarket(marketId: Long): Market? = dbQuery {
        val existingMarket = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        if (existingMarket != null) {
            if (existingMarket[MarketTable.isDeleted]) {
                null
            } else {
                MarketTable.update({ MarketTable.id eq marketId }) {
                    it[MarketTable.isDeleted] = true
                }
                Market(
                    id = existingMarket[MarketTable.id].value,
                    name = existingMarket[MarketTable.name]
                )
            }
        } else {
            throw NoSuchElementException("Market with ID $marketId not found.")
        }
    }

    suspend fun updateMarket(marketId: Long, marketName: String): Market = dbQuery {
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

    fun isValidMarketName(name: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z]+(\\s[a-zA-Z]+)*$")
        val matcher = pattern.matcher(name)
        return matcher.matches()
    }

    suspend fun getMarketById(marketId: Long): Market? = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            Market(
                id = it[MarketTable.id].value,
                name = it[MarketTable.name]
            )
        }
    }

}