package com.thechance.core.data.repository

import com.thechance.core.data.datasource.*
import com.thechance.core.data.model.*
import com.thechance.core.data.security.hashing.HashingService
import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.data.security.token.TokenClaim
import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.data.security.token.TokenService
import org.koin.core.component.KoinComponent

class HoneyMartRepositoryImp(
    private val marketDataSource: MarketDataSource,
    private val categoryDataSource: CategoryDataSource,
    private val productDataSource: ProductDataSource,
    private val userDataSource: UserDataSource,
    private val ownerDataSource: OwnerDataSource,
    private val hashingService: HashingService,
    private val tokenService: TokenService,
    private val tokenConfig: TokenConfig
) : HoneyMartRepository, KoinComponent {


    //region user
    override suspend fun createUser(userName: String, password: String): Boolean {
        val saltedHash = hashingService.generateSaltedHash(password)
        return userDataSource.createUser(userName, password, saltedHash)
    }

    override suspend fun isUserNameExists(userName: String): Boolean =
        userDataSource.isUserNameExists(userName)

    override suspend fun validateUser(name: String, password: String): String {
        val user = userDataSource.getUserByName(name)
        return user?.let { user ->
            val isValidPassword = hashingService.verify(
                value = password,
                saltedHash = SaltedHash(
                    hash = user.password,
                    salt = user.salt
                )
            )
            if (isValidPassword) {
                tokenService.generate(
                    config = tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = user.userId.toString()
                    )
                )
            } else {
                ""
            }
        } ?: ""
    }


    //endregion

    //region owner
    override suspend fun createOwner(userName: String, password: String): Owner =
        ownerDataSource.createOwner(userName, password)

    override suspend fun isOwnerNameExists(ownerName: String): Boolean =
        ownerDataSource.isOwnerNameExists(ownerName)

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

}