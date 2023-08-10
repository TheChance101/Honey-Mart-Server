package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.category.CategoriesTable
import com.thechance.core.data.datasource.database.tables.category.CategoryProductTable
import com.thechance.core.data.datasource.database.tables.product.GalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductGalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.data.datasource.mapper.toCategory
import com.thechance.core.data.datasource.mapper.toProduct
import com.thechance.core.data.repository.dataSource.ProductDataSource
import com.thechance.core.entity.Category
import com.thechance.core.entity.Image
import com.thechance.core.entity.Product
import com.thechance.core.utils.PAGE_SIZE
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent

class ProductDataSourceImp : ProductDataSource, KoinComponent {

    override suspend fun createProduct(
        productName: String, productPrice: Double, productQuantity: String, categoriesId: List<Long>
    ): Product = dbQuery {
        val newProduct = ProductTable.insert { productRow ->
            productRow[name] = productName
            productRow[price] = productPrice
            productRow[quantity] = productQuantity
        }

        insertCategoryForProduct(categoriesId, newProduct[ProductTable.id].value)

        Product(
            id = newProduct[ProductTable.id].value,
            name = newProduct[ProductTable.name],
            quantity = newProduct[ProductTable.quantity],
            price = newProduct[ProductTable.price],
            image = emptyList()
        )
    }

    private fun insertCategoryForProduct(categoriesId: List<Long>, productId: Long) {
        if (categoriesId.size == 1) {
            CategoryProductTable.insert { category ->
                category[CategoryProductTable.productId] = productId
                category[CategoryProductTable.categoryId] = categoriesId[0]
            }
        } else {
            CategoryProductTable.batchInsert(categoriesId) { categoryId ->
                this[CategoryProductTable.productId] = productId
                this[CategoryProductTable.categoryId] = categoryId
            }
        }
    }

    override suspend fun getAllProducts(): List<Product> = dbQuery {
        ProductTable.select { ProductTable.isDeleted eq false }.map { productRow ->
            val images = getProductImages(productRow[ProductTable.id].value)
            productRow.toProduct(images = images)
        }
    }

    override suspend fun getProduct(productId: Long): Product = dbQuery {
        ProductTable.select { ProductTable.id eq productId }.map { productRow ->
            val images = getProductImages(productRow[ProductTable.id].value)
            productRow.toProduct(images = images)
        }.single()
    }

    private fun getProductImages(productId: Long): List<Image> {
        return (GalleryTable innerJoin ProductGalleryTable).select { ProductGalleryTable.productId eq productId }
            .map { imageRow ->
                Image(
                    id = imageRow[GalleryTable.id].value,
                    imageUrl = imageRow[GalleryTable.imageUrl],
                )
            }
    }

    override suspend fun getAllProductsInCategory(categoryId: Long, page: Int): List<Product> = dbQuery {
        val offset = ((page - 1) * PAGE_SIZE).toLong()
        (ProductTable innerJoin CategoryProductTable).select { CategoryProductTable.categoryId eq categoryId }
            .limit(n = PAGE_SIZE, offset = offset).filterNot { it[ProductTable.isDeleted] }.map { productRow ->
                val images = getProductImages(productRow[ProductTable.id].value)
                productRow.toProduct(images)
            }
    }

    override suspend fun getAllCategoryForProduct(productId: Long): List<Category> = dbQuery {
        (CategoriesTable innerJoin CategoryProductTable).select { CategoryProductTable.productId eq productId }
            .map { it.toCategory() }
    }

    override suspend fun updateProduct(
        productId: Long, productName: String?, productPrice: Double?, productQuantity: String?
    ): Boolean = dbQuery {
        ProductTable.update({ ProductTable.id eq productId }) { productRow ->
            productName?.let { productRow[name] = it }
            productPrice?.let { productRow[price] = it }
            productQuantity?.let { productRow[quantity] = it }
        }
        true
    }

    override suspend fun updateProductCategory(productId: Long, categoryIds: List<Long>): Boolean = dbQuery {
        CategoryProductTable.deleteWhere { CategoryProductTable.productId eq productId }

        CategoryProductTable.batchInsert(categoryIds) { categoryId ->
            this[CategoryProductTable.productId] = productId
            this[CategoryProductTable.categoryId] = categoryId
        }
        true
    }

    override suspend fun deleteProduct(productId: Long): Boolean = dbQuery {
        ProductTable.update({ ProductTable.id eq productId }) { productRow ->
            productRow[isDeleted] = true
        }
        true
    }

    override suspend fun isDeleted(id: Long): Boolean? = dbQuery {
        val product = ProductTable.select { ProductTable.id eq id }.singleOrNull()
        product?.let {
            it[ProductTable.isDeleted]
        }
    }


    override suspend fun checkCategoriesInDb(categoryIds: List<Long>): Boolean = dbQuery {
        CategoriesTable.select { CategoriesTable.id inList categoryIds }.filterNot { it[CategoriesTable.isDeleted] }
            .toList().size == categoryIds.size
    }

    override suspend fun getProductMarketId(productId: Long): Long {
        return dbQuery {
            val categoryId = CategoryProductTable.select { CategoryProductTable.productId eq productId }
                .map { it[CategoryProductTable.categoryId].value }.first()

            CategoriesTable.select { CategoriesTable.id eq categoryId }.map { it[CategoriesTable.marketId].value }
                .single()
        }
    }

    override suspend fun addImageToGallery(imagesUrl: List<String>, productId: Long): Boolean {
        return dbQuery {
            GalleryTable.batchInsert(imagesUrl) { image ->
                this[GalleryTable.imageUrl] = image
            }.map { imageRow ->
                ProductGalleryTable.insert {
                    it[ProductGalleryTable.productId] = productId
                    it[ProductGalleryTable.galleryId] = imageRow[GalleryTable.id]
                }
            }
            true
        }
    }

    override suspend fun deleteImageFromProduct(productId: Long, imageId: Long): String {
        return dbQuery {
            ProductGalleryTable.deleteWhere {
                (ProductGalleryTable.productId eq productId) and (ProductGalleryTable.galleryId eq imageId)
            }
            val imageUrl = GalleryTable.select { GalleryTable.id eq imageId }.map { it[GalleryTable.imageUrl] }.single()
            GalleryTable.deleteWhere { GalleryTable.id eq imageId }
            imageUrl
        }
    }

    override suspend fun searchProductsByName(productName: String, page: Int): List<Product> = dbQuery {
        val offset = ((page - 1) * PAGE_SIZE).toLong()
        ProductTable.select { (ProductTable.name like "%$productName%") and (ProductTable.isDeleted eq false) }
            .limit(PAGE_SIZE, offset).map { productRow ->
                val images = getProductImages(productRow[ProductTable.id].value)
                productRow.toProduct(images = images)
            }
    }

    override suspend fun getAllProducts(page: Int): List<Product> = dbQuery {
        val offset = ((page - 1) * PAGE_SIZE).toLong()
        ProductTable.select { ProductTable.isDeleted eq false }.limit(PAGE_SIZE, offset).map { productRow ->
            val images = getProductImages(productRow[ProductTable.id].value)
            productRow.toProduct(images = images)
        }
    }

}