package com.thechance.core.data.datasource

import com.thechance.core.data.database.tables.CartTable
import com.thechance.core.data.database.tables.ProductTable
import com.thechance.core.data.model.Product
import com.thechance.core.data.model.User
import com.thechance.core.data.database.tables.NormalUserTable
import com.thechance.core.data.datasource.mapper.toProduct
import com.thechance.core.data.model.Cart
import com.thechance.core.data.model.ProductInCart
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent

class UserDataSourceImp : UserDataSource, KoinComponent {

    //region user
    override suspend fun createUser(userName: String, password: String): User {
        return dbQuery {
            val newUser = NormalUserTable.insert {
                it[NormalUserTable.userName] = userName
                it[NormalUserTable.password] = password
                it[NormalUserTable.isDeleted] = false
            }
            User(
                userId = newUser[NormalUserTable.id].value,
                userName = newUser[NormalUserTable.userName],
                password = newUser[NormalUserTable.password],
            )
        }
    }

    override suspend fun isUserNameExists(userName: String): Boolean {
        return dbQuery {
            NormalUserTable.select {
                NormalUserTable.userName eq userName
            }.count() > 0
        }
    }

    //endregion

    //region cart

    override suspend fun addToCart(userId: Long, productId: Long, quantity: Int): Boolean {
        return dbQuery {
            CartTable.insert {
                it[CartTable.userId] = userId
                it[CartTable.productId] = productId
                it[CartTable.quantity] = quantity
            }
            true
        }
    }

    override suspend fun isProductInCart(userId: Long, productId: Long): Boolean {
        return dbQuery {
            val product =
                CartTable.select { (CartTable.userId eq userId) and (CartTable.productId eq productId) }.singleOrNull()
            product != null
        }
    }

    override suspend fun getCart(userId: Long): Cart {
        return dbQuery {
            var total = 0.0
            val products = CartTable.select { CartTable.userId eq userId }
                .map { productRow ->
                    val product = getProduct(productId = productRow[CartTable.productId].value)
                    total += product.price
                    ProductInCart(
                        id = product.id,
                        name = product.name,
                        price = product.price,
                        quantity = productRow[CartTable.quantity]
                    )
                }
            Cart(products = products, total = total)
        }
    }

    private suspend fun getProduct(productId: Long): Product {
        return dbQuery {
            ProductTable.select { ProductTable.id eq productId }.map { productRow ->
                productRow.toProduct()
            }.single()
        }
    }

    override suspend fun deleteProductInCart(userId: Long, productId: Long): Boolean {
        return dbQuery {
            CartTable.deleteWhere { (CartTable.userId eq userId) and (CartTable.productId eq productId) }
            true
        }
    }

    override suspend fun updateCount(userId: Long, productId: Long, quantity: Int): Boolean {
        return dbQuery {
            CartTable.update({ (CartTable.userId eq userId) and (CartTable.productId eq productId) }) {
                it[CartTable.quantity] = quantity
            }
            true
        }
    }

    //endregion

}