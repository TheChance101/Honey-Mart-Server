package com.thechance.core.domain.usecase.cart

import org.koin.core.component.KoinComponent


data class CartUseCasesContainer(
    val addProductToCartUseCase: AddProductToCartUseCase,
    val getCartUseCase: GetCartUseCase,
    val deleteProductInCartUseCase: DeleteProductInCartUseCase,
) : KoinComponent