package com.thechance.core.data.usecase.product

import org.koin.core.component.KoinComponent

data class ProductUseCasesContainer(
    val createProductUseCase: CreateProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase,
    val updateProductUseCase: UpdateProductUseCase,
    val updateProductCategoryUseCase: UpdateProductCategoryUseCase,
    val getCategoriesForProductUseCase: GetCategoriesForProductUseCase
) : KoinComponent
