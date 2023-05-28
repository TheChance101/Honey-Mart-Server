package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.models.Product
import com.the_chance.data.models.ProductWithCategory
import com.the_chance.data.models.ProductsInCategory
import com.the_chance.data.services.validation.Error
import com.the_chance.data.services.validation.ErrorType
import com.the_chance.data.services.validation.ProductValidation
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.data.tables.CategoryProductTable
import com.the_chance.data.tables.ProductTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

class ProductService(private val database: Database) : BaseService(database, ProductTable, CategoryProductTable) {

    private val productValidation by lazy { ProductValidation() }

    suspend fun create(
        productName: String, productPrice: Double, productQuantity: String?, categoriesId: List<Long>?
    ): ProductWithCategory {
        val errors = productValidation.checkCreateValidation(
            productName = productName,
            productPrice = productPrice,
            productQuantity = productQuantity,
            categoriesId = categoriesId
        )
        return if (errors.isEmpty()) {
            if (isValidCategories(categoriesId!!)) {
                dbQuery {
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
                        category = getAllCategoryForProduct(newProduct[ProductTable.id].value)
                    )
                }
            } else {
                throw Throwable("not valid categories.")
            }
        } else {
            throw Throwable(errors.toString())
        }
    }

    suspend fun getAllProducts(): List<Product> {
        return dbQuery {
            ProductTable.select { ProductTable.isDeleted eq false }.map { productRow ->
                Product(
                    id = productRow[ProductTable.id].value,
                    name = productRow[ProductTable.name].toString(),
                    price = productRow[ProductTable.price],
                    quantity = productRow[ProductTable.quantity],
                )
            }
        }
    }

    suspend fun getAllCategoryForProduct(productId: Long?): List<Category> = dbQuery {
        (CategoriesTable innerJoin CategoryProductTable)
            .select { CategoryProductTable.productId eq productId }
            .map { categoryRow ->
                Category(
                    categoryId = categoryRow[CategoriesTable.id].value,
                    categoryName = categoryRow[CategoriesTable.name].toString(),
                )
            }
    }

    suspend fun updateProduct(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): String {
        if (productValidation.checkId(productId)) {
            if (!isDeleted(productId!!)) {
                val errors = productValidation.checkUpdateValidation(
                    productName = productName,
                    productPrice = productPrice,
                    productQuantity = productQuantity
                )
                return if (errors.isEmpty()) {
                    dbQuery {
                        ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                            productName?.let { productRow[name] = it }
                            productPrice?.let { productRow[price] = it }
                            productQuantity?.let { productRow[quantity] = it }
                        }
                    }
                    "Product Updated successfully."
                } else {
                    throw Throwable(errors.toString())
                }
            } else {
                throw Error(ErrorType.DELETED_ITEM)
            }
        } else {
            throw Error(ErrorType.NOT_FOUND)
        }
    }

    suspend fun deleteProduct(productId: Long?): String {
        return if (productValidation.checkId(productId)) {
            if (!isDeleted(productId!!)) {
                dbQuery {
                    ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                        productRow[isDeleted] = true
                    }
                }
                "Product Deleted successfully."
            } else {
                throw Error(ErrorType.DELETED_ITEM)
            }
        } else {
            throw Error(ErrorType.INVALID_INPUT)
        }
    }

    private suspend fun isDeleted(id: Long): Boolean = dbQuery {
        val product = ProductTable.select { ProductTable.id eq id }.singleOrNull()
        product?.let {
            it[ProductTable.isDeleted]
        } ?: throw Error(ErrorType.NOT_FOUND)
    }

    private suspend fun isValidCategories(categoryIds: List<Long>): Boolean {
        return dbQuery {
            CategoriesTable.select { CategoriesTable.id inList categoryIds }.toList().size == categoryIds.size
        }
    }

}