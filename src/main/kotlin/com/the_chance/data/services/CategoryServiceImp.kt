package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.data.tables.MarketTable
import com.the_chance.utils.toLowerCase
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent

class CategoryServiceImp(private val database: Database) : BaseService(database, CategoriesTable), CategoryService,
    KoinComponent {

    override suspend fun create(categoryName: String, marketId: Long): Category = dbQuery {
        val categoryList =
            getCategoriesByMarketId(marketId).filter { it.categoryName.toLowerCase() == categoryName.toLowerCase() }

        if (categoryList.isEmpty()) {
            val newCategory = CategoriesTable.insert {
                it[name] = categoryName
                it[isDeleted] = false
                it[this.marketId] = marketId
            }
            Category(
                categoryId = newCategory[CategoriesTable.id].value,
                categoryName = newCategory[CategoriesTable.name].toString(),
            )
        } else {
            throw NoSuchElementException("This category with name $categoryName already exist.")
        }

    }

    override suspend fun getCategoriesByMarketId(marketId: Long): List<Category> {
        return dbQuery {
            CategoriesTable.select {
                CategoriesTable.marketId eq marketId and
                        CategoriesTable.isDeleted.eq(false)
            }.map { resultRow ->
                Category(
                    categoryId = resultRow[CategoriesTable.id].value,
                    categoryName = resultRow[CategoriesTable.name].toString(),
                )
            }
        }
    }


    override suspend fun delete(categoryId: Long): Boolean = dbQuery {
        val category = CategoriesTable.select {
            CategoriesTable.id eq categoryId and Op.build { CategoriesTable.isDeleted eq false }
        }.singleOrNull()

        if (category != null) {
            CategoriesTable.update({ CategoriesTable.isDeleted eq false }) {
                it[isDeleted] = true
            } > 0
        } else {
            throw NoSuchElementException("This category id $categoryId not found.")
        }
    }

    override suspend fun update(categoryId: Long, categoryName: String): Boolean = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()

        if (category != null) {
            CategoriesTable.update({ CategoriesTable.id eq categoryId }) { categoryRow ->
                if (categoryName.isNotEmpty()) {
                    categoryRow[name] = categoryName
                }
            } > 0
        } else {
            throw NoSuchElementException("This category id $categoryId not found.")
        }
    }

    override suspend fun isDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw NoSuchElementException("Category with ID $marketId not found.")
    }
}