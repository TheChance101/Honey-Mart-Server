package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import com.thechance.core.data.datasource.database.tables.ProductReviewTable
import com.thechance.core.entity.review.Review
import com.thechance.core.entity.User
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toReview(): Review {
    return Review(
        reviewId = this[ProductReviewTable.id].value,
        reviewDate = this[ProductReviewTable.reviewDate],
        content = this[ProductReviewTable.content],
        rating = this[ProductReviewTable.rating],
        user = User(
            userId = this[NormalUserTable.id].value,
            email = this[NormalUserTable.email],
            fullName = this[NormalUserTable.fullName],
            profileImage = this[NormalUserTable.imageUrl],
            password = "",
            salt = "",
        )
    )
}