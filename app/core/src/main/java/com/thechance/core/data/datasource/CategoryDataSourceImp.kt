package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.mapper.toCategory
import com.thechance.core.data.datasource.mapper.toProduct
import com.thechance.core.data.datasource.database.tables.category.CategoriesTable
import com.thechance.core.data.datasource.database.tables.category.CategoryProductTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.data.repository.dataSource.CategoryDataSource
import com.thechance.core.entity.Category
import com.thechance.core.entity.Product
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.koin.core.component.KoinComponent

class CategoryDataSourceImp : CategoryDataSource, KoinComponent {

    override suspend fun createCategory(categoryName: String, marketId: Long, imageId: Int): Boolean = dbQuery {
        CategoriesTable.insert {
            it[name] = categoryName
            it[isDeleted] = false
            it[this.marketId] = marketId
            it[this.imageId] = imageId
        }
        true
    }

    override suspend fun getCategoriesByMarketId(marketId: Long): List<Category> = dbQuery {
        CategoriesTable
            .select {
                CategoriesTable.marketId eq marketId and
                        CategoriesTable.isDeleted.eq(false)
            }.map { resultRow -> resultRow.toCategory() }
    }

    override suspend fun getMarketIdByCategoryId(categoryId: Long): Long {
        return dbQuery {
            CategoriesTable.select { CategoriesTable.id eq categoryId }.map { it[CategoriesTable.marketId].value }
                .single()
        }
    }

    override suspend fun deleteCategory(categoryId: Long): Boolean = dbQuery {
        CategoriesTable.update({ (CategoriesTable.id eq categoryId) and (CategoriesTable.isDeleted eq false) }) {
            it[isDeleted] = true
        }
        (ProductTable innerJoin CategoryProductTable).update(
            { CategoryProductTable.categoryId eq categoryId }
        ) {
            it[ProductTable.isDeleted] = true
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


    override suspend fun isCategoryDeleted(categoryId: Long): Boolean? = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()
        category?.let {
            it[CategoriesTable.isDeleted]
        }
    }


    override suspend fun isCategoryNameUnique(categoryName: String, marketId: Long): Boolean = dbQuery {
        val category = CategoriesTable.select {
            CategoriesTable.name.lowerCase() eq categoryName.lowercase() and
                    CategoriesTable.isDeleted.eq(false)

        }.singleOrNull()
        if (category == null) {
            true
        } else category.toCategory().marketId != marketId
    }

}