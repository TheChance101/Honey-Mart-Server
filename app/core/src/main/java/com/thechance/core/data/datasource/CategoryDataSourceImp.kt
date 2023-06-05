package com.thechance.core.data.datasource

import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.tables.ProductTable
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.NoSuchElementException

class CategoryDataSourceImp : CategoryDataSource, KoinComponent {

    override suspend fun createCategory(
        categoryName: String,
        marketId: Long,
        imageId: Int
    ): Category = dbQuery {
        val newCategory = CategoriesTable.insert {
            it[name] = categoryName
            it[isDeleted] = false
            it[this.marketId] = marketId
            it[this.imageId] = imageId
        }
        Category(
            categoryId = newCategory[CategoriesTable.id].value,
            categoryName = newCategory[CategoriesTable.name].toString(),
            imageId = newCategory[CategoriesTable.imageId]
        )
    }

    override suspend fun getCategoriesByMarketId(marketId: Long?): List<Category> = dbQuery {
        CategoriesTable.select {
            CategoriesTable.marketId eq marketId and
                    CategoriesTable.isDeleted.eq(false)
        }.map { resultRow ->
            Category(
                categoryId = resultRow[CategoriesTable.id].value,
                categoryName = resultRow[CategoriesTable.name].toString(),
                imageId = resultRow[CategoriesTable.imageId]
            )
        }
    }

    override suspend fun deleteCategory(categoryId: Long?): Int = dbQuery {
        CategoriesTable.update({ CategoriesTable.isDeleted eq false }) {
            it[isDeleted] = true
        }
    }

    override suspend fun updateCategory(
        categoryId: Long?,
        categoryName: String?,
        marketId: Long?,
        imageId: Int?
    ): Boolean = dbQuery {
        CategoriesTable.update({ CategoriesTable.id eq categoryId }) { categoryRow ->
            categoryName?.let { categoryRow[name] = it }
            imageId?.let {
                categoryRow[CategoriesTable.imageId] = it
            }
        }
        true
    }

    override suspend fun getAllProductsInCategory(categoryId: Long?): List<Product> = dbQuery {
        (ProductTable innerJoin CategoryProductTable)
            .select { CategoryProductTable.categoryId eq categoryId }
            .map { productRow ->
                Product(
                    id = productRow[ProductTable.id].value,
                    name = productRow[ProductTable.name].toString(),
                    price = productRow[ProductTable.price],
                    quantity = productRow[ProductTable.quantity],
                )
            }
    }

    override suspend fun isCategoryDeleted(categoryId: Long): Boolean = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()
        category?.let {
            it[CategoriesTable.isDeleted]
        } ?: throw IdNotFoundException()
    }

    override suspend fun isMarketDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw NoSuchElementException()
    }

    override suspend fun isCategoryNameUnique(categoryName: String): Boolean = dbQuery {
        CategoriesTable.select {
            CategoriesTable.name.lowerCase() eq categoryName.lowercase() and
                    CategoriesTable.isDeleted.eq(false)
        }.singleOrNull()
    } == null


    /**
     * TODO: need check per Market.
     * */


}