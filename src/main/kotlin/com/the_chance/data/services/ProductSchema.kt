package com.the_chance.data.services

import com.the_chance.data.models.Product
import com.the_chance.data.tables.ProductTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*

class ProductService(private val database: Database) : BaseService() {
    init {
        transaction(database) {
            SchemaUtils.create(ProductTable)
        }
    }

    suspend fun create(productName: String, productPrice: Double): Product = dbQuery {
        val newProduct = ProductTable.insert {
            it[name] = productName
            it[price] = productPrice
        }
        Product(
            newProduct[ProductTable.id].value.toString(),
            newProduct[ProductTable.name],
            newProduct[ProductTable.price]
        )
    }

    suspend fun getAllProducts(): List<Product> {
        return dbQuery {
            ProductTable.selectAll().map {
                Product(
                    id = it[ProductTable.id].value.toString(),
                    name = it[ProductTable.name].toString(),
                    price = it[ProductTable.price]
                )
            }
        }
    }
}