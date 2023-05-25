package com.the_chance.data.services

import com.the_chance.data.models.Product
import com.the_chance.data.tables.ProductTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProductService(private val database: Database) : BaseService() {

    init {
        transaction(database) { SchemaUtils.create(ProductTable) }
    }

    suspend fun create(productName: String, productPrice: Double, productQuantity: String): Product = dbQuery {
        val newProduct = ProductTable.insert { productRow ->
            productRow[name] = productName
            productRow[price] = productPrice
            productRow[isDeleted] = false
            productRow[quantity] = productQuantity
        }
        Product(
            id = newProduct[ProductTable.id].value,
            name = newProduct[ProductTable.name],
            price = newProduct[ProductTable.price],
            quantity = newProduct[ProductTable.quantity],
        )
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
    ): Int {
        return dbQuery {
            ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                productName?.let { productRow[name] = it }
                productPrice?.let { productRow[price] = it }
                productQuantity?.let { productRow[quantity] = it }
            }
        }
    }

    suspend fun deleteProduct(productId: Long): Int {
        return dbQuery {
            ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                productRow[isDeleted] = true
            }
        }
    }

    fun isValid(productName: String, productPrice: Double, productQuantity: String): Boolean {
        return productName.length > 4 || productPrice > 0.0 || productQuantity.length > 4
    }
}