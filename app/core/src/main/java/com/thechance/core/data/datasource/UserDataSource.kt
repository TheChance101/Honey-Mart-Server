package com.thechance.core.data.datasource

import com.thechance.core.data.model.Product
import com.thechance.core.data.model.ProductInCart
import com.thechance.core.data.model.User
import com.thechance.core.data.utils.InvalidProductQuantityException

interface UserDataSource {

    suspend fun createUser(userName: String, password: String): User
    suspend fun isUserNameExists(userName: String): Boolean

    //region cart
    suspend fun createCart(): Boolean
    suspend fun getCart(cartId: Long): List<ProductInCart>

    suspend fun addToCart(cartId: Long, productId: Long, quantity: Int): Boolean

    suspend fun removeFromCart(cartId: Long, productId: Long): Boolean

    suspend fun changeQuantity(cartId: Long, productId: Long, quantity: Int): Boolean

    //endregion
}