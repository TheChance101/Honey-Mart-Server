package com.thechance.core.data.repository

import com.thechance.core.data.repository.dataSource.*
import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.*
import com.thechance.core.entity.order.*
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
    override suspend fun getWishList(wishListId: Long): List<Product> = userDataSource.getWishList(wishListId)

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
    override suspend fun getMarketIdByOwnerId(ownerId: Long): Long? = marketDataSource.getMarketIdByOwnerId(ownerId)

    override suspend fun createMarket(marketName: String, ownerId: Long): Boolean =
        marketDataSource.createMarket(marketName, ownerId)

    override suspend fun getAllMarkets(): List<Market> = marketDataSource.getAllMarkets()

    override suspend fun getCategoriesByMarket(marketId: Long): List<Category> =
        marketDataSource.getCategoriesByMarket(marketId)

    override suspend fun deleteMarket(marketId: Long): Boolean =
        marketDataSource.deleteMarket(marketId)

    override suspend fun updateMarket(marketId: Long, marketName: String?, imageUrl: String?): Boolean =
        marketDataSource.updateMarket(marketId, marketName, imageUrl)

    override suspend fun isMarketDeleted(marketId: Long): Boolean? =
        marketDataSource.isDeleted(marketId)

    override suspend fun getMarketId(productId: Long): Long? = marketDataSource.getMarketId(productId)

    override suspend fun getOwnerIdByMarketId(marketId: Long): Long? = marketDataSource.getOwnerIdByMarketId(marketId)

    override suspend fun addMarketImage(marketId: Long, imageUrl: String): Boolean =
        marketDataSource.addMarketImage(marketId, imageUrl)
    //endregion

    //region category
    override suspend fun createCategory(categoryName: String, marketId: Long, imageId: Int): Boolean =
        categoryDataSource.createCategory(categoryName = categoryName, marketId = marketId, imageId = imageId)

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
        productDataSource.getAllProductsInCategory(categoryId)

    override suspend fun isCategoryDeleted(categoryId: Long): Boolean? =
        categoryDataSource.isCategoryDeleted(categoryId)

    override suspend fun isCategoryNameUnique(categoryName: String, marketId: Long): Boolean =
        categoryDataSource.isCategoryNameUnique(categoryName, marketId)

    override suspend fun getMarketIdByCategoryId(categoryId: Long): Long =
        categoryDataSource.getMarketIdByCategoryId(categoryId)

    //endregion

    //region product
    override suspend fun createProduct(
        productName: String, productPrice: Double, productQuantity: String, categoriesId: List<Long>
    ): Product = productDataSource.createProduct(
        productName = productName, productPrice = productPrice, productQuantity = productQuantity,
        categoriesId = categoriesId
    )

    override suspend fun getAllProducts(): List<Product> = productDataSource.getAllProducts()

    override suspend fun getProduct(productId: Long) = productDataSource.getProduct(productId)

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

    override suspend fun addImageProduct(imagesUrl: List<String>, productId: Long): Boolean =
        productDataSource.addImageToGallery(imagesUrl, productId)

    override suspend fun deleteImageFromProduct(productId: Long, imageId: Long): String =
        productDataSource.deleteImageFromProduct(productId, imageId)
    //endregion

    //region order
    override suspend fun createOrder(cartId: Long, userId: Long): Boolean {
        val cart = getCart(cartId)
        return orderDataSource.createOrder(
            userId,
            getMarketId(cart.products.first().id)!!,
            cart.products.map {
                OrderItem(productId = it.id, count = it.count)
            },
            cart.total
        )
    }

    override suspend fun getOrdersForMarket(marketId: Long, state: Int): List<MarketOrder> =
        orderDataSource.getOrdersForMarket(marketId, state)

    override suspend fun getAllOrdersForMarket(marketId: Long): List<MarketOrder> =
        orderDataSource.getAllOrdersForMarket(marketId)

    override suspend fun getAllOrdersForUser(userId: Long) = orderDataSource.getAllOrdersForUser(userId)


    override suspend fun getOrdersForUser(userId: Long, state: Int) = orderDataSource.getOrdersForUser(userId, state)


    override suspend fun getOrderById(orderId: Long) = orderDataSource.getOrderById(orderId)


    override suspend fun updateOrderState(orderId: Long, newOrderState: Int) =
        orderDataSource.updateOrderState(orderId, newOrderState)


    override suspend fun isOrderExist(orderId: Long): Boolean = orderDataSource.isOrderExist(orderId)

    override suspend fun getOrderState(orderId: Long): Int = orderDataSource.getOrderState(orderId)

    //endregion

    //region image
    override suspend fun saveUserProfileImage(imageUrl: String, userId: Long): Boolean =
        userDataSource.saveUserProfileImage(imageUrl, userId)

    override suspend fun getUserProfileImage(userId: Long): String? =
        userDataSource.getUserProfileImage(userId)

    //endregion

}