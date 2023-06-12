package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.*
import com.thechance.core.data.security.hashing.SaltedHash

interface UserDataSource {
    //region user
    suspend fun createUser(saltedHash: SaltedHash, fullName: String, email: String): Boolean

    suspend fun isUserExist(userId: Long): Boolean

    suspend fun getUserByEmail(email: String): User

    suspend fun isEmailExists(email: String): Boolean
    //endregion

    //region cart

    suspend fun getCartId(userId: Long): Long?

    suspend fun getCart(cartId: Long): Cart

    suspend fun addToCart(cartId: Long, marketId: Long, productId: Long, count: Int): Boolean

    suspend fun isProductInCart(cartId: Long, productId: Long): Boolean

    suspend fun deleteProductInCart(cartId: Long, productId: Long): Boolean

    suspend fun updateCount(cartId: Long, productId: Long, count: Int): Boolean

    suspend fun createCart(userId: Long): Long

    suspend fun getCartMarketId(cartId: Long): Long?

    suspend fun deleteCart(cartId: Long): Boolean
    //endregion

    //region wishList
    suspend fun getWishList(wishListId: Long): List<ProductInWishList>
    suspend fun deleteProductFromWishList(wishListId: Long, productId: Long): Boolean
    suspend fun getWishListId(userId: Long): Long?
    suspend fun addProductToWishList(wishListId: Long, productId: Long): Boolean
    suspend fun isProductInWishList(wishListId: Long, productId: Long): Boolean
    suspend fun createWishList(userId: Long): Long


    //endregion
    suspend fun deleteAllProductsInCart(cartId: Long): Boolean
}