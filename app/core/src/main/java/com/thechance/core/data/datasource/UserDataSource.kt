package com.thechance.core.data.datasource

import com.thechance.core.data.model.Cart
import com.thechance.core.data.model.Product
import com.thechance.core.data.model.ProductInCart
import com.thechance.core.data.model.User
import com.thechance.core.data.utils.InvalidProductQuantityException

interface UserDataSource {

    suspend fun createUser(userName: String, password: String): User
    suspend fun isUserNameExists(userName: String): Boolean

    //region cart

    suspend fun getCartId(userId: Long): Long?

    suspend fun getCart(cartId: Long): Cart

    suspend fun addToCart(cartId: Long, marketId: Long, productId: Long, count: Int): Boolean

    suspend fun isProductInCart(cartId: Long, productId: Long): Boolean

    suspend fun deleteProductInCart(cartId: Long, productId: Long): Boolean

    suspend fun updateCount(cartId: Long, productId: Long, count: Int): Boolean

    suspend fun createCart(userId: Long): Long
    //endregion
}