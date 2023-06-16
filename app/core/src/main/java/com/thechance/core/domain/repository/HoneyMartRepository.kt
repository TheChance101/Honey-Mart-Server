package com.thechance.core.domain.repository

import com.thechance.core.entity.*

interface HoneyMartRepository {

    //region cart
    suspend fun getCartId(userId: Long): Long?
    suspend fun getCart(cartId: Long): Cart
    suspend fun isProductInCart(cartId: Long, productId: Long): Boolean
    suspend fun addToCart(cartId: Long, productId: Long, marketId: Long, count: Int): Boolean
    suspend fun deleteProductInCart(cartId: Long, productId: Long): Boolean
    suspend fun updateProductCountInCart(cartId: Long, productId: Long, count: Int): Boolean
    suspend fun createCart(userId: Long): Long
    suspend fun getCartMarketId(cartId: Long): Long?

    suspend fun deleteCart(cartId: Long): Boolean

    //endregion

    //region WishList
    suspend fun getWishList(wishListId: Long): List<ProductInWishList>
    suspend fun deleteProductFromWishList(wishListId: Long, productId: Long): Boolean
    suspend fun getWishListId(userId: Long): Long?
    suspend fun addToWishList(wishListId: Long, productId: Long): Boolean
    suspend fun createWishList(userId: Long): Long
    suspend fun isProductInWishList(wishListId: Long, productId: Long): Boolean
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

    suspend fun getProductMarketId(productId: Long): Long
    //endregion

    //region order
    suspend fun createOrder(cartId: Long, userId: Long): Boolean
    suspend fun getAllOrdersForMarket(marketId: Long): List<Order>
    suspend fun getAllOrdersForUser(userId: Long): List<Order>
    suspend fun getOrderById(orderId: Long): OrderDetails
    suspend fun updateOrderState(orderId: Long, newOrderState: Int): Boolean
    suspend fun isOrderExist(orderId: Long): Boolean

    //endregion

    suspend fun deleteAllProductsInCart(cartId: Long): Boolean


    suspend fun saveUserProfileImage(imageUrl: String, userId: Long): Boolean
    suspend fun getUserProfileImage(userId: Long): String?
}