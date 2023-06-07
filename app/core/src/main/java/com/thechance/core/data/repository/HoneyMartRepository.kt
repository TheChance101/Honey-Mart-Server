package com.thechance.core.data.repository

import com.thechance.core.data.model.*

interface HoneyMartRepository {

    //region market
    suspend fun createMarket(marketName: String): Market
    suspend fun getAllMarkets(): List<Market>
    suspend fun getCategoriesByMarket(marketId: Long): List<Category>
    suspend fun deleteMarket(marketId: Long): Boolean
    suspend fun updateMarket(marketId: Long, marketName: String): Market
    suspend fun isMarketDeleted(marketId: Long): Boolean?
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

    //region order
    suspend fun createOrder(
        marketId: Long,
        orderDate:String,
        totalPrice: Double,
        isPaid: Boolean,
        products: List<OrderItem>
    ): Order

    suspend fun getAllOrdersForMarket(marketId:Long):List<Order>
    suspend fun cancelOrder(orderId:Long)
    //end region
}