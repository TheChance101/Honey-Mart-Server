package com.the_chance.data.services

import com.the_chance.data.models.Market
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.data.tables.MarketTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.regex.Pattern

class MarketService(database: Database) : BaseService(database, MarketTable, CategoriesTable) {

    suspend fun createMarket(marketName: String): Market = dbQuery {
        val newMarket = MarketTable.insert {
            it[name] = marketName
            it[isDeleted] = false
        }
        Market(
            marketId = newMarket[MarketTable.id].value,
            marketName = newMarket[MarketTable.name],
            categories = emptyList()
        )
    }

    suspend fun getAllMarkets(): List<Market> = dbQuery {
        MarketTable
            .join(
                CategoriesTable,
                JoinType.INNER,
                additionalConstraint = { MarketTable.id eq CategoriesTable.marketId })
            .select {
                (MarketTable.isDeleted eq false) and (CategoriesTable.isDeleted eq false)

            }.map {
                Market(
                    marketId = it[MarketTable.id].value,
                    marketName = it[MarketTable.name],
                )
            }
    }


//    suspend fun getAllMarkets(): List<Market> = dbQuery {
//        MarketTable.select { MarketTable.isDeleted eq false }.map {
//            Market(
//                marketId = it[MarketTable.id].value,
//                marketName = it[MarketTable.name],
//            )
//        }
//    }

    suspend fun deleteMarket(marketId: Long): Boolean = dbQuery {
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


    suspend fun updateMarket(marketId: Long, marketName: String): Market = dbQuery {
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

    fun isValidMarketName(name: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z]+(\\s[a-zA-Z]+)*$")
        val matcher = pattern.matcher(name)
        return matcher.matches()
    }

    suspend fun isDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw NoSuchElementException("Market with ID $marketId not found.")
    }

}