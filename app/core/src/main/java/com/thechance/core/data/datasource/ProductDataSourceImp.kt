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
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent

class ProductDataSourceImp : ProductDataSource, KoinComponent {
    override suspend fun createProduct(
        productName: String, productPrice: Double, productQuantity: String, categoriesId: List<Long>
    ): Product = dbQuery {
        val newProduct = ProductTable.insert { productRow ->
            productRow[name] = productName
            productRow[price] = productPrice
            productRow[quantity] = productQuantity
        }

        CategoryProductTable.batchInsert(categoriesId) { categoryId ->
            this[CategoryProductTable.productId] = newProduct[ProductTable.id]
            this[CategoryProductTable.categoryId] = categoryId
        }

        Product(
            id = newProduct[ProductTable.id].value,
            name = newProduct[ProductTable.name],
            price = newProduct[ProductTable.price],
            quantity = newProduct[ProductTable.quantity],
        )
    }

    override suspend fun getAllProducts(): List<Product> = dbQuery {
        ProductTable.select { ProductTable.isDeleted eq false }.map { productRow ->
            productRow.toProduct()
        }
    }

    override suspend fun getAllCategoryForProduct(productId: Long): List<Category> = dbQuery {
        (CategoriesTable innerJoin CategoryProductTable)
            .select { CategoryProductTable.productId eq productId }
            .map { it.toCategory() }
    }

    override suspend fun updateProduct(
        productId: Long, productName: String?, productPrice: Double?, productQuantity: String?
    ): Boolean = dbQuery {
        ProductTable.update({ ProductTable.id eq productId }) { productRow ->
            productName?.let { productRow[name] = it }
            productPrice?.let { productRow[price] = it }
            productQuantity?.let { productRow[quantity] = it }
        }
        true
    }

    override suspend fun updateProductCategory(productId: Long, categoryIds: List<Long>): Boolean =
        dbQuery {
            CategoryProductTable.deleteWhere { CategoryProductTable.productId eq productId }

            CategoryProductTable.batchInsert(categoryIds) { categoryId ->
                this[CategoryProductTable.productId] = productId
                this[CategoryProductTable.categoryId] = categoryId
            }
            true
        }

    override suspend fun deleteProduct(productId: Long): Boolean = dbQuery {
        ProductTable.update({ ProductTable.id eq productId }) { productRow ->
            productRow[isDeleted] = true
        }
        true
    }

    override suspend fun isDeleted(id: Long): Boolean? = dbQuery {
        val product = ProductTable.select { ProductTable.id eq id }.singleOrNull()
        product?.let {
            it[ProductTable.isDeleted]
        }
    }


    override suspend fun checkCategoriesInDb(categoryIds: List<Long>): Boolean = dbQuery {
        CategoriesTable.select { CategoriesTable.id inList categoryIds }
            .filterNot { it[CategoriesTable.isDeleted] }.toList().size == categoryIds.size
    }


}