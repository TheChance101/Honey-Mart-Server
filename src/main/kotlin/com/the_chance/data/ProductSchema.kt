package com.the_chance.data

import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlinx.serialization.Serializable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*

@Serializable
data class Product(
    val id: String,
    val name: String,
    val price: Double,
)

class ProductService(private val database: Database) {
    object Products : UUIDTable() {
        val name = text("name")
        val price = double("price")
    }

    init {
        transaction(database) {
            SchemaUtils.create(Products)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(productName: String, productPrice: Double): Product = dbQuery {
        val newProduct = Products.insert{
            it[name] = productName
            it[price] = productPrice
        }
        Product(
            newProduct[Products.id].value.toString(),
            newProduct[Products.name],
            newProduct[Products.price]
        )
    }

    suspend fun getAllProducts(): List<Product> {
        return dbQuery {
            Products.selectAll().map {
                Product(
                    id = it[Products.id].value.toString(),
                    name = it[Products.name].toString(),
                    price = it[Products.price]
                )
            }
        }
    }


}