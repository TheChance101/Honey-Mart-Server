package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import com.thechance.core.data.datasource.database.tables.ProductReviewTable
import com.thechance.core.data.datasource.mapper.toReview
import com.thechance.core.data.repository.dataSource.ReviewDataSource
import com.thechance.core.entity.review.Review
import com.thechance.core.entity.review.ReviewStatistic
import com.thechance.core.utils.PAGE_SIZE
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.koin.core.component.KoinComponent

class ReviewDataSourceImp : ReviewDataSource, KoinComponent {
    override suspend fun addProductReview(
        userId: Long,
        productId: Long,
        orderId: Long,
        content: String,
        rating: Int,
    ): Boolean = dbQuery {
        ProductReviewTable.insert {
            it[this.userId] = userId
            it[this.productId] = productId
            it[this.orderId] = orderId
            it[this.content] = content
            it[this.rating] = rating
        }
        true
    }

    override suspend fun updateProductReview(
        userId: Long,
        productId: Long,
        content: String,
        newRating: Int
    ): Boolean = dbQuery {
        val updatedRowCount = ProductReviewTable.update({
            (ProductReviewTable.userId eq userId) and
                    (ProductReviewTable.productId eq productId)
        }) {
            it[this.content] = content
            it[this.rating] = newRating
        }

        updatedRowCount > 0 // Return true if at least one row was updated
    }

    override suspend fun isReviewExists(userId: Long, productId: Long): Boolean = dbQuery {
        val count = ProductReviewTable.select {
            (ProductReviewTable.userId eq userId) and
                    (ProductReviewTable.productId eq productId)
        }.count()

        count > 0 // Return true if at least one review exists
    }

    override suspend fun getProductReviews(productId: Long, page: Int): List<Review> = dbQuery {
        val offset = ((page - 1) * PAGE_SIZE).toLong()
        ProductReviewTable.join(NormalUserTable, JoinType.FULL)
            .select { ProductReviewTable.productId eq productId }
            .orderBy(ProductReviewTable.rating, SortOrder.DESC)
            .limit(PAGE_SIZE, offset)
            .map {
                it.toReview()
            }
    }

    override suspend fun getReviewsStatisticsForProduct(productId: Long): ReviewStatistic = dbQuery {
        val reviews = ProductReviewTable.select { ProductReviewTable.productId eq productId }.toList()
        val reviewsCount = reviews.size
        val totalRating = reviews.sumOf { it[ProductReviewTable.rating] }

        // Calculate the count of each rating
        val oneStarCount = reviews.count { it[ProductReviewTable.rating] == 1 }
        val twoStarsCount = reviews.count { it[ProductReviewTable.rating] == 2 }
        val threeStarsCount = reviews.count { it[ProductReviewTable.rating] == 3 }
        val fourStarsCount = reviews.count { it[ProductReviewTable.rating] == 4 }
        val fiveStarsCount = reviews.count { it[ProductReviewTable.rating] == 5 }

        // Calculate the average rating
        val averageRating = if (reviewsCount > 0) totalRating.toFloat() / reviewsCount else 0f

        ReviewStatistic(
            averageRating = averageRating,
            reviewsCount = reviewsCount,
            oneStarCount = oneStarCount,
            twoStarsCount = twoStarsCount,
            threeStarsCount = threeStarsCount,
            fourStarsCount = fourStarsCount,
            fiveStarsCount = fiveStarsCount
        )
    }

    override suspend fun getProductAverageRating(productId: Long): Float = dbQuery {
        val reviews = ProductReviewTable.select { ProductReviewTable.productId eq productId }.toList()
        val reviewsCount = reviews.size
        val totalRating = reviews.sumOf { it[ProductReviewTable.rating] }

        // Calculate the average rating
        val averageRating = if (reviewsCount > 0) totalRating.toFloat() / reviewsCount else 0f

        averageRating
    }
}