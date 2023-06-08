package com.thechance.core.data.datasource

import com.thechance.core.data.database.tables.CartTable
import com.thechance.core.data.database.tables.MarketTable
import com.thechance.core.data.database.tables.ProductTable
import com.thechance.core.data.model.Product
import com.thechance.core.data.model.User
import com.thechance.core.data.database.tables.UserTable
import com.thechance.core.data.datasource.mapper.toProduct
import com.thechance.core.data.model.Market
import com.thechance.core.data.model.ProductInCart
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent

class UserDataSourceImp : UserDataSource, KoinComponent {
    override suspend fun createUser(userName: String, password: String): User {
        return dbQuery {
            val newUser = UserTable.insert {
                it[UserTable.userName] = userName
                it[UserTable.password] = password
                it[UserTable.isDeleted] = false
            }
            User(
                userId = newUser[UserTable.id].value,
                userName = newUser[UserTable.userName],
                password = newUser[UserTable.password],
            )
        }
    }

    override suspend fun isUserNameExists(userName: String): Boolean {
        return dbQuery {
            UserTable.select {
                UserTable.userName eq userName
            }.count() > 0
        }
    }

    override suspend fun createCart(): Boolean {
        TODO("Not yet implemented")
    }

    //region cart
    override suspend fun getCart(cartId: Long): List<ProductInCart> {
        return dbQuery {
            CartTable.selectAll()
                .map { productRow ->
                    val product = getProduct(productId = productRow[CartTable.productId].value)
                    ProductInCart(
                        id = product.id,
                        name = product.name,
                        price = product.price,
                        quantity = productRow[CartTable.quantity]
                    )
                }
        }
    }

    private suspend fun getProduct(productId: Long): Product {
        return dbQuery {
            ProductTable.select { ProductTable.id eq productId }.map { productRow ->
                productRow.toProduct()
            }.single()
        }
    }

    override suspend fun addToCart(cartId: Long, productId: Long, quantity: Int): Boolean {
        return dbQuery {
            CartTable.insert {
                it[CartTable.productId] = productId
                it[CartTable.quantity] = quantity
            }
            true
        }
    }

    override suspend fun removeFromCart(cartId: Long, productId: Long): Boolean {
        return dbQuery {
            CartTable.deleteWhere { CartTable.productId eq productId }
            true
        }
    }

    override suspend fun changeQuantity(cartId: Long, productId: Long, quantity: Int): Boolean {
        return dbQuery {
            CartTable.update({ CartTable.productId eq productId }) {
                it[CartTable.quantity] = quantity
            }
            true
        }
    }
    //endregion

}