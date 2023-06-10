package com.thechance.core.data.usecase.wishlist

import org.koin.core.component.KoinComponent

data class WishListUseCaseContainer(
    val addProductToWishListUseCase: AddProductToWishListUseCase,
) : KoinComponent