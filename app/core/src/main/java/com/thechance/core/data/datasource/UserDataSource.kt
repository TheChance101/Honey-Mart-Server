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

    suspend fun getCart(userId: Long): Cart

    suspend fun addToCart(userId: Long, productId: Long, quantity: Int): Boolean

    suspend fun isProductInCart(userId: Long, productId: Long): Boolean

    suspend fun deleteProductInCart(userId: Long, productId: Long): Boolean

    suspend fun updateCount(userId: Long, productId: Long, quantity: Int): Boolean

    //endregion
}