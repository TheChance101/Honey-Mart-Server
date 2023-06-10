package com.thechance.core.domain.usecase.wishlist

import org.koin.core.component.KoinComponent

data class WishListUseCaseContainer(
    val addProductToWishListUseCase: AddProductToWishListUseCase,
    val deleteProductFromWishListUseCase: DeleteProductFromWishListUseCase,
    val getWishListUseCase: GetWishListUseCase
) : KoinComponent