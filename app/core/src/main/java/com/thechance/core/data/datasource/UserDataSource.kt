package com.thechance.core.data.datasource

import com.thechance.core.data.model.Cart
import com.thechance.core.data.model.Product
import com.thechance.core.data.model.ProductInCart
import com.thechance.core.data.model.User
import com.thechance.core.data.utils.InvalidProductQuantityException
import com.thechance.core.data.security.hashing.SaltedHash

interface UserDataSource {

    suspend fun createUser(userName: String, saltedHash: SaltedHash, fullName: String, email: String): Boolean
    suspend fun isUserNameExists(userName: String): Boolean

    suspend fun isUserExist(userId: Long): Boolean

    suspend fun getUserByName(userName: String): User

    suspend fun isEmailExists(email: String): Boolean

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