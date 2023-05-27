package com.the_chance.data.services

import com.the_chance.data.models.Product
import com.the_chance.data.models.ProductsInCategory
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.data.tables.CategoryProductTable
import com.the_chance.data.tables.ProductTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class CategoryProductService(private val database: Database) : BaseService(database, CategoryProductTable) {
//    SchemaUtils.create(Products, Category, CategoryProductTable)

    suspend fun create(categoryId: Long?, productId: Long?): Int {
        return dbQuery {
            val newProductCategory = CategoryProductTable.insert {
                it[CategoryProductTable.productId] = productId!!
                it[CategoryProductTable.categoryId] = categoryId!!
            }
            newProductCategory.insertedCount
        }
    }

    suspend fun getProductsFromCategory(categoryId: Long?): ProductsInCategory {
        val categoryProducts = dbQuery {
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
        val categoryName = dbQuery {
            CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()?.get(CategoriesTable.name) ?: ""
        }

        return ProductsInCategory(
            categoryId = categoryId!!,
            categoryName = categoryName,
            products = categoryProducts
        )
    }
}