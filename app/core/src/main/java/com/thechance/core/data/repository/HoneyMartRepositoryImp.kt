package com.thechance.core.data.repository

import com.thechance.core.data.datasource.CategoryDataSource
import com.thechance.core.data.datasource.MarketDataSource
import com.thechance.core.data.datasource.OrderDataSource
import com.thechance.core.data.datasource.ProductDataSource
import com.thechance.core.data.model.*
import org.koin.core.component.KoinComponent

class HoneyMartRepositoryImp(
    private val marketDataSource: MarketDataSource,
    private val categoryDataSource: CategoryDataSource,
    private val productDataSource: ProductDataSource,
    private val orderDataSource: OrderDataSource
) : HoneyMartRepository, KoinComponent {

    //region market
    override suspend fun createMarket(marketName: String): Market = marketDataSource.createMarket(marketName)
    override suspend fun getAllMarkets(): List<Market> = marketDataSource.getAllMarkets()

    override suspend fun getCategoriesByMarket(marketId: Long): List<Category> =
        marketDataSource.getCategoriesByMarket(marketId)

    override suspend fun deleteMarket(marketId: Long): Boolean =
        marketDataSource.deleteMarket(marketId)

    override suspend fun updateMarket(marketId: Long, marketName: String): Market =
        marketDataSource.updateMarket(marketId, marketName)

    override suspend fun isMarketDeleted(marketId: Long): Boolean? =
        marketDataSource.isDeleted(marketId)

//endregion

    //region category
    override suspend fun createCategory(categoryName: String, marketId: Long, imageId: Int): Category =
        categoryDataSource.createCategory(
            categoryName = categoryName, marketId = marketId, imageId = imageId
        )

    override suspend fun getCategoriesByMarketId(marketId: Long): List<Category> =
        categoryDataSource.getCategoriesByMarketId(marketId)

    override suspend fun deleteCategory(categoryId: Long): Boolean =
        categoryDataSource.deleteCategory(categoryId)

    override suspend fun updateCategory(
        categoryId: Long, categoryName: String?, marketId: Long, imageId: Int?
    ): Boolean =
        categoryDataSource.updateCategory(
            categoryId = categoryId, categoryName = categoryName, marketId = marketId, imageId = imageId
        )

    override suspend fun getAllProductsInCategory(categoryId: Long): List<Product> =
        categoryDataSource.getAllProductsInCategory(categoryId)

    override suspend fun isCategoryDeleted(categoryId: Long): Boolean? =
        categoryDataSource.isCategoryDeleted(categoryId)

    override suspend fun isCategoryNameUnique(categoryName: String): Boolean =
        categoryDataSource.isCategoryNameUnique(categoryName)

//endregion

    //region product
    override suspend fun createProduct(
        productName: String, productPrice: Double, productQuantity: String, categoriesId: List<Long>
    ): Product =
        productDataSource.createProduct(
            productName = productName, productPrice = productPrice, productQuantity = productQuantity,
            categoriesId = categoriesId
        )

    override suspend fun getAllProducts(): List<Product> = productDataSource.getAllProducts()

    override suspend fun getAllCategoryForProduct(productId: Long): List<Category> =
        productDataSource.getAllCategoryForProduct(productId)

    override suspend fun updateProduct(
        productId: Long, productName: String?, productPrice: Double?, productQuantity: String?
    ): Boolean = productDataSource.updateProduct(
        productId = productId, productName = productName, productPrice = productPrice,
        productQuantity = productQuantity
    )

    override suspend fun updateProductCategory(productId: Long, categoryIds: List<Long>): Boolean =
        productDataSource.updateProductCategory(productId, categoryIds)

    override suspend fun deleteProduct(productId: Long): Boolean =
        productDataSource.deleteProduct(productId)

    override suspend fun checkCategoriesInDb(categoryIds: List<Long>): Boolean =
        productDataSource.checkCategoriesInDb(categoryIds)

    override suspend fun isProductDeleted(id: Long): Boolean? =
        productDataSource.isDeleted(id)

    //endregion

    //region order
    override suspend fun createOrder(
        marketId: Long,
        orderDate: String,
        totalPrice: Double,
        isPaid: Boolean,
        products: List<OrderItem>
    ): Order =
        orderDataSource.createOrder(marketId, orderDate, totalPrice, isPaid, products)

    override suspend fun getAllOrdersForMarket(marketId: Long): List<Order> =
        orderDataSource.getAllOrdersForMarket(marketId)

    override suspend fun cancelOrder(orderId: Long) {
        orderDataSource.cancelOrder(orderId)
    }
    //end region
//endregion

}