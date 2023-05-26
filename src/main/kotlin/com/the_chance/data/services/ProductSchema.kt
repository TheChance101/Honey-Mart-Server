package com.the_chance.data.services

import com.the_chance.data.models.Product
import com.the_chance.data.services.validation.ProductValidation
import com.the_chance.data.tables.ProductTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProductService(private val database: Database) : BaseService() {

    private val productValidation by lazy { ProductValidation() }

    init {
        transaction(database) { SchemaUtils.create(ProductTable) }
    }

    suspend fun create(productName: String, productPrice: Double, productQuantity: String?): Product {
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

    suspend fun updateProduct(
        productId: Long,
        productName: String?,
        productPrice: Double?,
        productQuantity: String?
    ): String {
        val errors = productValidation.checkUpdateValidation(
            productId = productId,
            productName = productName,
            productPrice = productPrice,
            productQuantity = productQuantity
        )
        if (errors.isEmpty()) {
            val result = dbQuery {
                ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                    productName?.let { productRow[name] = it }
                    productPrice?.let { productRow[price] = it }
                    productQuantity?.let { productRow[quantity] = it }
                }
            }
            return if (result == productValidation.VALID_QUERY) {
                "Product Updated successfully."
            } else {
                throw Throwable("Not valid Product Id")
            }
        } else {
            throw Throwable(errors.toString())
        }
    }

    suspend fun deleteProduct(productId: Long): String {
        return if (productValidation.checkId(productId)) {
            if (dbQuery {
                    ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                        productRow[isDeleted] = true
                    }
                } == productValidation.VALID_QUERY) {
                "Product Deleted successfully."
            } else {
                throw Throwable("The product id was not found.")
            }
        } else {
            throw Throwable("Not valid Product Id")
        }
    }
}