package com.thechance.core.data.datasource

import com.thechance.core.data.model.WishList

interface WishListDataSource {
    suspend fun createWishList(productId: Long, userId: Long): WishList
  //  suspend fun getAllWishList(userId: Long): List<WishList>
   // suspend fun deleteFromWishList(productId: Long): Boolean
  suspend fun isProductInWishList(productId: Long): Boolean
}