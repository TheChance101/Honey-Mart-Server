package com.thechance.core.data.repository

import com.thechance.core.entity.*
import com.thechance.core.data.repository.dataSource.*
import com.thechance.core.domain.repository.HoneyMartRepository
import org.koin.core.component.KoinComponent

class HoneyMartRepositoryImp(
    private val marketDataSource: MarketDataSource,
    private val categoryDataSource: CategoryDataSource,
    private val productDataSource: ProductDataSource,
    private val orderDataSource: OrderDataSource,
    private val userDataSource: UserDataSource
) : HoneyMartRepository, KoinComponent {

    //region cart
    override suspend fun getCartId(userId: Long): Long? = userDataSource.getCartId(userId)

    override suspend fun getCart(cartId: Long): Cart = userDataSource.getCart(cartId)

    override suspend fun isProductInCart(cartId: Long, productId: Long): Boolean =
        userDataSource.isProductInCart(cartId, productId)


    override suspend fun addToCart(cartId: Long, productId: Long, marketId: Long, count: Int): Boolean =
        userDataSource.addToCart(cartId = cartId, productId = productId, marketId = marketId, count = count)


    override suspend fun deleteProductInCart(cartId: Long, productId: Long): Boolean =
        userDataSource.deleteProductInCart(cartId, productId)

    override suspend fun updateProductCountInCart(cartId: Long, productId: Long, count: Int): Boolean =
        userDataSource.updateCount(cartId, productId, count)

    override suspend fun createCart(userId: Long): Long = userDataSource.createCart(userId)

    override suspend fun getCartMarketId(cartId: Long): Long? = userDataSource.getCartMarketId(cartId)
    override suspend fun deleteCart(cartId: Long): Boolean = userDataSource.deleteCart(cartId)

    override suspend fun deleteAllProductsInCart(cartId: Long): Boolean =
        userDataSource.deleteAllProductsInCart(cartId)


    //endregion

    //region WishList
    override suspend fun getWishList(wishListId: Long): List<ProductInWishList> = userDataSource.getWishList(wishListId)

    override suspend fun deleteProductFromWishList(wishListId: Long, productId: Long): Boolean =
        userDataSource.deleteProductFromWishList(wishListId, productId)

    override suspend fun getWishListId(userId: Long): Long? = userDataSource.getWishListId(userId)
    override suspend fun addToWishList(wishListId: Long, productId: Long): Boolean =
        userDataSource.addProductToWishList(wishListId, productId)

    override suspend fun createWishList(userId: Long): Long = userDataSource.createWishList(userId)
    override suspend fun isProductInWishList(wishListId: Long, productId: Long): Boolean =
        userDataSource.isProductInWishList(wishListId, productId)

    //endregion

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

    override suspend fun getMarketId(productId: Long): Long? = marketDataSource.getMarketId(productId)

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

    override suspend fun getProductMarketId(productId: Long): Long = productDataSource.getProductMarketId(productId)

    //endregion

    //endregion

    //region order
    override suspend fun createOrder(cartId: Long, userId: Long): Boolean {
        val cart = getCart(cartId)
        return orderDataSource.createOrder(
            cart.total, cart.products.map {
                OrderItem(productId = it.id, count = it.count, marketId = getMarketId(it.id)!!)
            }, userId
        )
    }

    override suspend fun getAllOrdersForMarket(marketId: Long): List<Order> =
        orderDataSource.getAllOrdersForMarket(marketId)

    override suspend fun cancelOrder(orderId: Long): Boolean =
        orderDataSource.cancelOrder(orderId)

    override suspend fun isOrderExist(orderId: Long): Boolean =
        orderDataSource.isOrderExist(orderId)
    //end region
//endregion

}