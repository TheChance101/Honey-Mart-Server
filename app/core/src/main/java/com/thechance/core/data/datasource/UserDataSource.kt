package com.thechance.core.data.datasource

import com.thechance.core.data.model.*
import com.thechance.core.data.security.hashing.SaltedHash

interface UserDataSource {

    suspend fun createUser(userName: String, saltedHash: SaltedHash): Boolean
    suspend fun isUserNameExists(userName: String): Boolean

    suspend fun isUserExist(userId: Long): Boolean

    suspend fun getUserByName(userName: String): User

    //region cart

    suspend fun getCartId(userId: Long): Long?

    suspend fun getCart(cartId: Long): Cart

    suspend fun addToCart(cartId: Long, marketId: Long, productId: Long, count: Int): Boolean

    suspend fun isProductInCart(cartId: Long, productId: Long): Boolean

    suspend fun deleteProductInCart(cartId: Long, productId: Long): Boolean

    suspend fun updateCount(cartId: Long, productId: Long, count: Int): Boolean

    suspend fun createCart(userId: Long): Long
    //endregion

    //region wishList
    suspend fun getWishListId(userId: Long): Long?
    suspend fun addProductToWishList(wishListId: Long, productId: Long): Boolean
    suspend fun isProductInWishList(wishListId:Long,productId: Long): Boolean
    suspend fun createWishList(userId: Long): Long

    //  suspend fun getAllWishList(userId: Long): List<WishList>
    // suspend fun deleteFromWishList(productId: Long): Boolean

    //endregion
}