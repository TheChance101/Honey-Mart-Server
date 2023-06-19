package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.mapper.toCategory
import com.thechance.core.data.datasource.mapper.toProduct
import com.thechance.core.entity.*
import com.thechance.core.data.datasource.database.tables.category.CategoriesTable
import com.thechance.core.data.datasource.database.tables.category.CategoryProductTable
import com.thechance.core.data.datasource.database.tables.product.GalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductGalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.data.repository.dataSource.ProductDataSource
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent

class ProductDataSourceImp : ProductDataSource, KoinComponent {
    override suspend fun createProduct(
        productName: String,
        productPrice: Double,
        productQuantity: String,
        categoriesId: List<Long>,
        images: List<Long>
    ): Boolean = dbQuery {
        val newProduct = ProductTable.insert { productRow ->
            productRow[name] = productName
            productRow[price] = productPrice
            productRow[quantity] = productQuantity
        }

        insertCategoryForProduct(categoriesId, newProduct[ProductTable.id].value)

        ProductGalleryTable.batchInsert(images) { image ->
            this[ProductGalleryTable.productId] = newProduct[ProductTable.id].value
            this[ProductGalleryTable.galleryId] = image
        }
        true
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

    private fun getProductImages(productId: Long): List<Image> {
        return (GalleryTable innerJoin ProductGalleryTable)
            .select { ProductGalleryTable.productId eq productId }
            .map { imageRow ->
                Image(
                    id = imageRow[GalleryTable.id].value,
                    imageUrl = imageRow[GalleryTable.imageUrl],
                )
            }
    }

    override suspend fun getAllProductsInCategory(categoryId: Long): List<Product> = dbQuery {
        (ProductTable innerJoin CategoryProductTable)
            .select { CategoryProductTable.categoryId eq categoryId }
            .filterNot { it[ProductTable.isDeleted] }
            .map { productRow ->
                val images = getProductImages(productRow[ProductTable.id].value)
                productRow.toProduct(images)
            }
    }

    override suspend fun getAllCategoryForProduct(productId: Long): List<Category> = dbQuery {
        (CategoriesTable innerJoin CategoryProductTable)
            .select { CategoryProductTable.productId eq productId }
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

    override suspend fun updateProductCategory(productId: Long, categoryIds: List<Long>): Boolean =
        dbQuery {
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
        CategoriesTable.select { CategoriesTable.id inList categoryIds }
            .filterNot { it[CategoriesTable.isDeleted] }.toList().size == categoryIds.size
    }

    override suspend fun getProductMarketId(productId: Long): Long {
        return dbQuery {
            val categoryId = CategoryProductTable.select { CategoryProductTable.productId eq productId }
                .map { it[CategoryProductTable.categoryId].value }.single()

            CategoriesTable.select { CategoriesTable.id eq categoryId }.map { it[CategoriesTable.marketId].value }
                .single()
        }
    }

    override suspend fun addImageToGallery(imageUrl: String): Image {
        return dbQuery {
            val newImage = GalleryTable.insert { image ->
                image[GalleryTable.imageUrl] = imageUrl
            }

            Image(
                id = newImage[GalleryTable.id].value,
                imageUrl = newImage[GalleryTable.imageUrl]
            )
        }
    }

    override suspend fun deleteImageFromProduct(productId: Long, imageId: Long): Boolean {
        return dbQuery {
            ProductGalleryTable.deleteWhere {
                (ProductGalleryTable.productId eq productId) and
                        (ProductGalleryTable.galleryId eq imageId)
            }
            GalleryTable.deleteWhere { GalleryTable.id eq imageId }
            true
        }
    }

}