package com.thechance.core.data.datasource

import com.thechance.core.data.mapper.toCategory
import com.thechance.core.data.mapper.toProductWithCategory
import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product
import com.thechance.core.data.model.ProductWithCategory
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.ProductTable
import com.thechance.core.data.utils.ItemNotAvailableException
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent

class ProductDataSourceImp : ProductDataSource, KoinComponent {
    override suspend fun createProduct(
        productName: String,
        productPrice: Double,
        productQuantity: String?,
        categoriesId: List<Long>
    ): ProductWithCategory = dbQuery {
        val newProduct = ProductTable.insert { productRow ->
            productRow[name] = productName
            productRow[price] = productPrice
            productRow[quantity] = productQuantity
        }

        CategoryProductTable.batchInsert(categoriesId) { categoryId ->
            this[CategoryProductTable.productId] = newProduct[ProductTable.id]
            this[CategoryProductTable.categoryId] = categoryId
        }

        ProductWithCategory(
            id = newProduct[ProductTable.id].value,
            name = newProduct[ProductTable.name],
            price = newProduct[ProductTable.price],
            quantity = newProduct[ProductTable.quantity],
            category = (CategoriesTable innerJoin CategoryProductTable)
                .select { CategoryProductTable.productId eq newProduct[ProductTable.id].value }
                .map { categoryRow ->
                    Category(
                        categoryId = categoryRow[CategoriesTable.id].value,
                        categoryName = categoryRow[CategoriesTable.name].toString(),
                        imageId = categoryRow[CategoriesTable.imageId]
                    )
                }
        )
    }

    override suspend fun getAllProducts(): List<Product> =
        dbQuery {
            ProductTable.select { ProductTable.isDeleted eq false }.map { productRow ->
                Product(
                    id = productRow[ProductTable.id].value,
                    name = productRow[ProductTable.name].toString(),
                    price = productRow[ProductTable.price],
                    quantity = productRow[ProductTable.quantity],
                )
            }
        }

    override suspend fun getAllCategoryForProduct(productId: Long?): List<Category> = dbQuery {
        (CategoriesTable innerJoin CategoryProductTable)
            .select { CategoryProductTable.productId eq productId }
            .map { it.toCategory() }
    }

    override suspend fun updateProduct(
        productId: Long?,
        productName: String?,
        productPrice: Double?,
        productQuantity: String?
    ): Int = dbQuery {
        ProductTable.update({ ProductTable.id eq productId }) { productRow ->
            productName?.let { productRow[name] = it }
            productPrice?.let { productRow[price] = it }
            productQuantity?.let { productRow[quantity] = it }
        }
    }

    override suspend fun updateProductCategory(productId: Long, categoryIds: List<Long>): ProductWithCategory =
        dbQuery {
            CategoryProductTable.deleteWhere { CategoryProductTable.productId eq productId }

            CategoryProductTable.batchInsert(categoryIds) { categoryId ->
                this[CategoryProductTable.productId] = productId
                this[CategoryProductTable.categoryId] = categoryId
            }

            val product = ProductTable.select { ProductTable.id eq productId }.map {
                val categories = (CategoriesTable innerJoin CategoryProductTable)
                    .select { CategoryProductTable.productId eq it[ProductTable.id].value }
                    .map { it.toCategory() }
                it.toProductWithCategory(categories)
            }.single()
            product
        }

    override suspend fun deleteProduct(productId: Long?): Int = dbQuery {
        ProductTable.update({ ProductTable.id eq productId }) { productRow ->
            productRow[isDeleted] = true
        }
    }

    override suspend fun isDeleted(id: Long): Boolean {
        val product = dbQuery {
            ProductTable.select { ProductTable.id eq id }.singleOrNull()
                ?: throw ItemNotAvailableException()
        }
        return product[ProductTable.isDeleted]
    }

    override suspend fun checkCategoriesInDb(categoryIds: List<Long>): Boolean = dbQuery {
        CategoriesTable.select { CategoriesTable.id inList categoryIds }
            .filterNot { it[CategoriesTable.isDeleted] }.toList().size == categoryIds.size
    }

}