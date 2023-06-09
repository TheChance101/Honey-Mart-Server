package com.thechance.core.data.datasource

import com.thechance.core.data.database.tables.CartProductTable
import com.thechance.core.data.database.tables.CartTable
import com.thechance.core.data.database.tables.ProductTable
import com.thechance.core.data.model.Product
import com.thechance.core.data.model.User
import com.thechance.core.data.database.tables.NormalUserTable
import com.thechance.core.data.datasource.mapper.toProduct
import com.thechance.core.data.model.Cart
import com.thechance.core.data.model.ProductInCart
import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class UserDataSourceImp : UserDataSource, KoinComponent {
    override suspend fun createUser(userName: String, saltedHash: SaltedHash): Boolean {
        return dbQuery {
            NormalUserTable.insert {
                it[NormalUserTable.userName] = userName
                it[NormalUserTable.password] = saltedHash.hash
                it[NormalUserTable.salt] = saltedHash.salt
            }
            true
        }
    }

    override suspend fun isUserNameExists(userName: String): Boolean {
        return dbQuery {
            NormalUserTable.select {
                NormalUserTable.userName eq userName
            }.count() > 0
        }
    }

    override suspend fun isUserExist(userId: Long): Boolean {
        return dbQuery {
            val user = NormalUserTable.select { NormalUserTable.id eq userId }.singleOrNull()
            user != null
        }
    }

    override suspend fun getUserByName(userName: String): User {
        return dbQuery {
            NormalUserTable.select(NormalUserTable.userName eq userName).map {
                User(
                    userId = it[NormalUserTable.id].value,
                    userName = it[NormalUserTable.userName],
                    password = it[NormalUserTable.password],
                    salt = it[NormalUserTable.salt]
                )
            }.single()

        }
    }
    //endregion

    //region cart

    override suspend fun createCart(userId: Long): Long {
        return dbQuery {
            val newCart = CartTable.insert {
                it[CartTable.userId] = userId
            }
            newCart[CartTable.id].value
        }
    }

    override suspend fun getCartId(userId: Long): Long? {
        return dbQuery {
            CartTable.select { CartTable.userId eq userId }.map { it[CartTable.id].value }.singleOrNull()
        }
    }

    override suspend fun addToCart(cartId: Long, marketId: Long, productId: Long, count: Int): Boolean {
        return dbQuery {
            CartProductTable.insert {
                it[CartProductTable.cartId] = cartId
                it[CartProductTable.productId] = productId
                it[CartProductTable.marketId] = marketId
                it[CartProductTable.count] = count
            }
            true
        }
    }

    override suspend fun isProductInCart(cartId: Long, productId: Long): Boolean {
        return dbQuery {
            val product =
                CartProductTable.select { (CartProductTable.cartId eq cartId) and (CartProductTable.productId eq productId) }
                    .singleOrNull()
            product != null
        }
    }

    override suspend fun getCart(cartId: Long): Cart {
        return dbQuery {
            var total = 0.0
            val products = CartProductTable.select { CartProductTable.cartId eq cartId }
                .map { productRow ->
                    val product = getProduct(productId = productRow[CartProductTable.productId].value)
                    val cartProduct = ProductInCart(
                        id = product.id,
                        name = product.name,
                        price = product.price,
                        count = productRow[CartProductTable.count]
                    )
                    total += product.price * cartProduct.count

                    cartProduct
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

    override suspend fun deleteProductInCart(cartId: Long, productId: Long): Boolean {
        return dbQuery {
            CartProductTable.deleteWhere { (CartProductTable.cartId eq cartId) and (CartProductTable.productId eq productId) }
            true
        }
    }

    override suspend fun updateCount(cartId: Long, productId: Long, count: Int): Boolean {
        return dbQuery {
            CartProductTable.update({ (CartProductTable.cartId eq cartId) and (CartProductTable.productId eq productId) }) {
                it[CartProductTable.count] = count
            }
            true
        }
    }

    //endregion

}