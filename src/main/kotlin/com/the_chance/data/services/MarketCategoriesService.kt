package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.data.tables.MarketTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

class MarketCategoriesService(database: Database) : BaseService(database, MarketTable, CategoriesTable) {

    suspend fun getCategoriesByMarket(marketId: Long): List<Category> = dbQuery {
        CategoriesTable
            .join(MarketTable, JoinType.INNER, additionalConstraint = {
                CategoriesTable.marketId eq MarketTable.id
            })
            .select {
                (CategoriesTable.marketId eq marketId) and
                        (CategoriesTable.isDeleted eq false) and
                        (MarketTable.isDeleted eq false)
            }
            .map {
                Category(
                    id = it[CategoriesTable.id].value,
                    name = it[CategoriesTable.name],
                    image = it[CategoriesTable.image],
                    marketId = it[CategoriesTable.marketId].value
                )
            }
    }


}