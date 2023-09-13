package com.thechance.api.model.mapper

import com.thechance.api.model.ProductModel
import com.thechance.api.model.ProductWithAverageRatingModel
import com.thechance.api.model.ProductWithReviewsModel
import com.thechance.core.entity.product.Product
import com.thechance.core.entity.product.ProductWithAverageRating
import com.thechance.core.entity.product.ProductWithReviews


internal fun Product.toApiProductModel(): ProductModel {
    return ProductModel(
        id = id,
        name = name,
        description = description,
        price = price,
        images = images.map { it.imageUrl })
}

internal fun ProductWithAverageRating.toApiProductWithAverageRatingModel(): ProductWithAverageRatingModel {
    return ProductWithAverageRatingModel(
        id = id,
        name = name,
        description = description,
        price = price,
        images = images.map { it.imageUrl },
        averageRating = averageRating
    )
}

internal fun ProductWithReviews.toApiProductWithReviews(): ProductWithReviewsModel {
    return ProductWithReviewsModel(
        id = id,
        name = name,
        description = description,
        price = price,
        images = image.map { it.imageUrl },
        reviews = reviews.toApiReviewsWithStatisticsModel()
    )
}