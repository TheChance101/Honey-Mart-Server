package com.thechance.core.domain.repository

import com.thechance.core.entity.Cart
import com.thechance.core.entity.Category
import com.thechance.core.entity.Product
import com.thechance.core.entity.coupon.Coupon
import com.thechance.core.entity.coupon.MarketCoupon
import com.thechance.core.entity.coupon.UserCoupon
import com.thechance.core.entity.market.Market
import com.thechance.core.entity.order.MarketOrder
import com.thechance.core.entity.order.OrderDetails
import com.thechance.core.entity.order.UserOrder
import java.time.LocalDateTime

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
    suspend fun getWishList(wishListId: Long): List<Product>
    suspend fun deleteProductFromWishList(wishListId: Long, productId: Long): Boolean
    suspend fun getWishListId(userId: Long): Long?
    suspend fun addToWishList(wishListId: Long, productId: Long): Boolean
    suspend fun createWishList(userId: Long): Long
    suspend fun isProductInWishList(wishListId: Long, productId: Long): Boolean
    //endregion

    //region market
    suspend fun getMarketIdByOwnerId(ownerId: Long): Long?
    suspend fun createMarket(ownerId: Long, name: String, address: String, description: String): Long?
    suspend fun getAllMarkets(page: Int): List<Market>
    suspend fun getCategoriesByMarket(marketId: Long): List<Category>
    suspend fun deleteMarket(marketId: Long): Boolean
    suspend fun updateMarket(marketId: Long, marketName: String?, description: String?): Boolean
    suspend fun updateMarketImage(marketId: Long, imageUrl: String?): Boolean
    suspend fun updateMarketLocation(marketId: Long, latitude: Double?, longitude: Double?, address: String?): Boolean
    suspend fun updateMarketStatus(marketId: Long, state: Boolean): Boolean
    suspend fun isMarketDeleted(marketId: Long): Boolean?
    suspend fun getMarketId(productId: Long): Long?
    suspend fun getOwnerIdByMarketId(marketId: Long): Long?
    suspend fun addMarketImage(marketId: Long, imageUrl: String): Boolean
    suspend fun getMarket(marketId: Long): Market?
    suspend fun getProductsCountForMarket(marketId: Long): Int
    //endregion


    //region category
    suspend fun createCategory(categoryName: String, marketId: Long, imageId: Int): Boolean
    suspend fun getCategoriesByMarketId(marketId: Long): List<Category>
    suspend fun deleteCategory(categoryId: Long): Boolean
    suspend fun updateCategory(
        categoryId: Long,
        categoryName: String?,
        marketId: Long,
        imageId: Int?
    ): Boolean

    suspend fun getAllProductsInCategory(categoryId: Long, page: Int): List<Product>
    suspend fun isCategoryDeleted(categoryId: Long): Boolean?
    suspend fun isCategoryNameUnique(categoryName: String, marketId: Long): Boolean
    suspend fun getMarketIdByCategoryId(categoryId: Long): Long
    //endregion

    //region product
    suspend fun createProduct(
        productName: String, productPrice: Double, productQuantity: String, categoriesId: List<Long>, marketsId: Long
    ): Product

    suspend fun getProduct(productId: Long): Product

    suspend fun getAllCategoryForProduct(productId: Long): List<Category>

    suspend fun updateProduct(
        productId: Long, productName: String?, productPrice: Double?, productQuantity: String?
    ): Boolean

    suspend fun updateProductCategory(productId: Long, categoryIds: List<Long>): Boolean
    suspend fun deleteProduct(productId: Long): Boolean
    suspend fun checkCategoriesInDb(categoryIds: List<Long>): Boolean
    suspend fun isProductDeleted(id: Long): Boolean?

    suspend fun getProductMarketId(productId: Long): Long

    suspend fun addImageProduct(imagesUrl: List<String>, productId: Long): Boolean

    suspend fun deleteImageFromProduct(productId: Long, imageId: Long): String

    suspend fun searchProductsByName(productName: String, sortOrder: String?, page: Int): List<Product>

    suspend fun getMostRecentProducts(): List<Product>

    suspend fun getAllProducts(page: Int): List<Product>
    suspend fun deleteProductImages(productId: Long): List<String>
    //endregion

    //region order
    suspend fun createOrder(userId: Long, cart: Cart, totalPrice: Double): Boolean
    suspend fun getOrdersForMarket(marketId: Long, state: Int): List<MarketOrder>
    suspend fun getOrdersForUser(userId: Long, state: Int): List<UserOrder>
    suspend fun getOrderById(orderId: Long): OrderDetails
    suspend fun updateOrderState(orderId: Long, newOrderState: Int): Boolean
    suspend fun isOrderExist(orderId: Long): Boolean
    suspend fun getOrderState(orderId: Long): Int
    suspend fun getAllOrdersForUser(userId: Long): List<UserOrder>
    suspend fun getAllOrdersForMarket(marketId: Long): List<MarketOrder>

    //endregion

    suspend fun deleteAllProductsInCart(cartId: Long): Boolean

    suspend fun saveUserProfileImage(imageUrl: String, userId: Long): Boolean
    suspend fun getUserProfileImage(userId: Long): String?


    //endregion

    //region coupons
    suspend fun addCoupon(
        marketId: Long,
        productId: Long,
        count: Int,
        discountPercentage: Double,
        expirationDate: LocalDateTime
    ): Boolean

    suspend fun getCouponsForUser(userId: Long): List<UserCoupon>
    suspend fun getClippedCouponsForUser(userId: Long): List<UserCoupon>
    suspend fun getCouponsForMarket(marketId: Long): List<MarketCoupon>
    suspend fun deleteCoupon(couponId: Long): Boolean
    suspend fun clipCoupon(couponId: Long, userId: Long): Boolean
    suspend fun useCoupon(couponId: Long, userId: Long): Boolean
    suspend fun isCouponClipped(couponId: Long, userId: Long): Boolean
    suspend fun isValidCoupon(couponId: Long): Boolean
    suspend fun getAllValidCoupons(): List<Coupon>
    suspend fun getMarketProductsWithoutValidCoupons(marketId: Long): List<Product>
    suspend fun searchMarketProductsWithoutValidCoupons(marketId: Long, productName: String): List<Product>
    //end coupons region
    suspend fun restoreMarket(marketId: Long): Boolean
}