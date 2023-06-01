package com.thechance.core.data.service

import com.thechance.api.model.Category
import com.thechance.api.model.Product
import com.thechance.api.model.ProductWithCategory
import com.thechance.api.service.ProductService
import com.thechance.api.utils.ErrorType
import com.thechance.core.data.tables.ProductTable
import org.koin.core.component.KoinComponent
import com.thechance.api.utils.Error
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.validation.product.ProductValidation
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class ProductServiceImp(private val productValidation: ProductValidation) :
    BaseService(ProductTable, CategoryProductTable), ProductService,
    KoinComponent {

    override suspend fun create(
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
                        category = (CategoriesTable innerJoin CategoryProductTable)
                            .select { CategoryProductTable.productId eq newProduct[ProductTable.id].value }
                            .map { categoryRow ->
                                Category(
                                    categoryId = categoryRow[CategoriesTable.id].value,
                                    categoryName = categoryRow[CategoriesTable.name].toString(),
                                )
                            }
                    )
                }
            } else {
                throw Throwable("not valid categories.")
            }
        } else {
            throw Throwable(errors.toString())
        }
    }

    override suspend fun getAllProducts(): List<Product> {
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

    override suspend fun getAllCategoryForProduct(productId: Long?): List<Category> {
        return if (productValidation.checkId(productId)) {
            if (!isDeleted(productId!!)) {
                dbQuery {
                    (CategoriesTable innerJoin CategoryProductTable)
                        .select { CategoryProductTable.productId eq productId }
                        .map { categoryRow ->
                            Category(
                                categoryId = categoryRow[CategoriesTable.id].value,
                                categoryName = categoryRow[CategoriesTable.name].toString()
                            )
                        }
                }
            } else {
                throw Error(ErrorType.DELETED_ITEM)
            }
        } else {
            throw Error(ErrorType.INVALID_INPUT)
        }
    }

    override suspend fun updateProduct(
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

    override suspend fun updateProductCategory(productId: Long?, categoryIds: List<Long>): ProductWithCategory {
        return dbQuery {
            if (isValidCategories(categoryIds)) {
                CategoryProductTable.deleteWhere { CategoryProductTable.productId eq productId }

                CategoryProductTable.batchInsert(categoryIds) { categoryId ->
                    this[CategoryProductTable.productId] = productId!!
                    this[CategoryProductTable.categoryId] = categoryId
                }
                val product = ProductTable.select { ProductTable.id eq productId }.map {
                    ProductWithCategory(
                        id = it[ProductTable.id].value,
                        name = it[ProductTable.name],
                        price = it[ProductTable.price],
                        quantity = it[ProductTable.quantity],
                        category = (CategoriesTable innerJoin CategoryProductTable)
                            .select { CategoryProductTable.productId eq it[ProductTable.id].value }
                            .map { categoryRow ->
                                Category(
                                    categoryId = categoryRow[CategoriesTable.id].value,
                                    categoryName = categoryRow[CategoriesTable.name].toString(),
                                )
                            }
                    )
                }.single()
                product
            } else {
                throw Throwable()
            }
        }
    }

    override suspend fun deleteProduct(productId: Long?): String {
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

    /**
     * validate that each categoryId is found and not deleted
     * */
    private suspend fun isValidCategories(categoryIds: List<Long>): Boolean {
        return dbQuery {
            CategoriesTable.select { CategoriesTable.id inList categoryIds }
                .filterNot { it[CategoriesTable.isDeleted] }.toList().size == categoryIds.size
        }
    }

}