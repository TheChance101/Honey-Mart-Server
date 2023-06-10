package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.mapper.toCategory
import com.thechance.core.data.datasource.mapper.toProduct
import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product
import com.thechance.core.data.database.tables.category.CategoriesTable
import com.thechance.core.data.database.tables.category.CategoryProductTable
import com.thechance.core.data.database.tables.ProductTable
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.koin.core.component.KoinComponent

class CategoryDataSourceImp : CategoryDataSource, KoinComponent {

    override suspend fun createCategory(
        categoryName: String, marketId: Long, imageId: Int
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

    override suspend fun getCategoriesByMarketId(marketId: Long): List<Category> = dbQuery {
        CategoriesTable
            .select {
                CategoriesTable.marketId eq marketId and
                        CategoriesTable.isDeleted.eq(false)
            }.map { resultRow -> resultRow.toCategory() }
    }

    override suspend fun deleteCategory(categoryId: Long): Boolean = dbQuery {
        CategoriesTable.update({ CategoriesTable.isDeleted eq false }) {
            it[isDeleted] = true
        }
        true
    }

    override suspend fun updateCategory(
        categoryId: Long, categoryName: String?, marketId: Long, imageId: Int?
    ): Boolean = dbQuery {
        CategoriesTable.update({ CategoriesTable.id eq categoryId }) { categoryRow ->
            categoryName?.let { categoryRow[name] = it }
            imageId?.let {
                categoryRow[CategoriesTable.imageId] = it
            }
        }
        true
    }

    override suspend fun getAllProductsInCategory(categoryId: Long): List<Product> = dbQuery {
        (ProductTable innerJoin CategoryProductTable)
            .select { CategoryProductTable.categoryId eq categoryId }
            .filterNot { it[ProductTable.isDeleted] }
            .map { productRow ->
                productRow.toProduct()
            }
    }

    override suspend fun isCategoryDeleted(categoryId: Long): Boolean? = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()
        category?.let {
            it[CategoriesTable.isDeleted]
        }
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