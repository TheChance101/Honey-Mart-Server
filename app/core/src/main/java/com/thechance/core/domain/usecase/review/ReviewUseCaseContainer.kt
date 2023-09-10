package com.thechance.core.domain.usecase.review

import org.koin.core.component.KoinComponent

data class ReviewUseCaseContainer(
    val getProductReviewsUseCase: GetProductReviewsUseCase,
    val addProductReviewUseCase: AddProductReviewUseCase
) : KoinComponent