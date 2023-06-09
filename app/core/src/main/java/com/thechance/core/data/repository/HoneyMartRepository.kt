package com.thechance.core.data.repository

import com.thechance.core.data.model.*

interface HoneyMartRepository {

    //region user
    suspend fun createUser(userName: String, password: String): User
    suspend fun isUserNameExists(userName: String): Boolean

    suspend fun getCartId(userId: Long): Long?
    suspend fun getCart(cartId: Long): Cart
    suspend fun isProductInCart(cartId: Long, productId: Long): Boolean
    suspend fun addToCart(cartId: Long, productId: Long, marketId: Long, count: Int): Boolean

    suspend fun deleteProductInCart(cartId: Long, productId: Long): Boolean

    suspend fun updateProductCountInCart(cartId: Long, productId: Long, count: Int): Boolean

    suspend fun createCart(userId: Long): Long
    //endregion


    //region owner
    suspend fun createOwner(userName: String, password: String): Owner
    suspend fun isOwnerNameExists(ownerName: String): Boolean

    //endregion


    //region market
    suspend fun createMarket(marketName: String): Market
    suspend fun getAllMarkets(): List<Market>
    suspend fun getCategoriesByMarket(marketId: Long): List<Category>
    suspend fun deleteMarket(marketId: Long): Boolean
    suspend fun updateMarket(marketId: Long, marketName: String): Market
    suspend fun isMarketDeleted(marketId: Long): Boolean?
    suspend fun getMarketId(productId: Long): Long?
    //endregion


    //region category
    suspend fun createCategory(categoryName: String, marketId: Long, imageId: Int): Category
    suspend fun getCategoriesByMarketId(marketId: Long): List<Category>
    suspend fun deleteCategory(categoryId: Long): Boolean
    suspend fun updateCategory(categoryId: Long, categoryName: String?, marketId: Long, imageId: Int?): Boolean
    suspend fun getAllProductsInCategory(categoryId: Long): List<Product>
    suspend fun isCategoryDeleted(categoryId: Long): Boolean?
    suspend fun isCategoryNameUnique(categoryName: String): Boolean
    //endregion

    //region product
    suspend fun createProduct(
        productName: String, productPrice: Double, productQuantity: String, categoriesId: List<Long>
    ): Product

    suspend fun getAllProducts(): List<Product>
    suspend fun getAllCategoryForProduct(productId: Long): List<Category>
    suspend fun updateProduct(
        productId: Long, productName: String?, productPrice: Double?, productQuantity: String?
    ): Boolean

    suspend fun updateProductCategory(productId: Long, categoryIds: List<Long>): Boolean
    suspend fun deleteProduct(productId: Long): Boolean
    suspend fun checkCategoriesInDb(categoryIds: List<Long>): Boolean

    suspend fun isProductDeleted(id: Long): Boolean?
    //endregion
}