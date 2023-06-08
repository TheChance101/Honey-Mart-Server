package com.thechance.core.data.usecase.cart

import org.koin.core.component.KoinComponent


data class CartUseCasesContainer(
    val addProductToCartUseCase: AddProductToCartUseCase,
    val getCartUseCase: GetCartUseCase,
) : KoinComponent