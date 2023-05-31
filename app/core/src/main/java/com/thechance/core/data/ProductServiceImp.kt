package com.thechance.core.data

import com.thechance.core.data.validation.ProductValidation
import com.thechance.api.model.Product
import com.thechance.api.service.ProductService
import com.thechance.api.utils.*
import com.thechance.core.data.tables.ProductTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class ProductServiceImp(private val database: CoreDataBase) : BaseService(database, ProductTable), ProductService,
    KoinComponent {

    private val productValidation by lazy { ProductValidation() }

    override suspend fun create(productName: String, productPrice: Double, productQuantity: String?): Product {
        val errors = productValidation.checkCreateValidation(
            productName = productName,
            productPrice = productPrice,
            productQuantity = productQuantity
        )
        return if (errors.isEmpty()) {
            dbQuery {
                val newProduct = ProductTable.insert { productRow ->
                    productRow[name] = productName
                    productRow[price] = productPrice
                    productRow[quantity] = productQuantity
                }
                Product(
                    id = newProduct[ProductTable.id].value,
                    name = newProduct[ProductTable.name],
                    price = newProduct[ProductTable.price],
                    quantity = newProduct[ProductTable.quantity],
                )
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
                throw ItemNotAvailableException("Item No longer Available")
            }
        } else {
            throw IdNotFoundException("Id Not Found")
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
                throw ItemNotAvailableException("Item No longer Available")
            }
        } else {
            throw InvalidInputException("Invalid Input")
        }
    }

    private suspend fun isDeleted(id: Long): Boolean = dbQuery {
        val product = ProductTable.select { ProductTable.id eq id }.singleOrNull()
        product?.let {
            it[ProductTable.isDeleted]
        } ?: throw IdNotFoundException("Id Not Found")
    }
}