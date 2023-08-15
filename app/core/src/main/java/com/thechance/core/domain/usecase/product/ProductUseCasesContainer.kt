package com.thechance.core.domain.usecase.product

import org.koin.core.component.KoinComponent

data class ProductUseCasesContainer(
    val createProductUseCase: CreateProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase,
    val updateProductUseCase: UpdateProductUseCase,
    val updateProductCategoryUseCase: UpdateProductCategoryUseCase,
    val getCategoriesForProductUseCase: GetCategoriesForProductUseCase,
    val addImageProductUseCase: AddImageProductUseCase,
    val deleteImageFromProductUseCase: DeleteImageFromProductUseCase,
    val updateProductImageUseCase:UpdateProductImageUseCase,
    val getProductDetailsUseCase: GetProductDetailsUseCase,
    val getMostRecentProductsUseCase: GetMostRecentProductsUseCase,
    val searchProductsByNameUseCase: SearchProductsByNameUseCase,
    val getAllProductsUseCase: GetAllProductsUseCase
) : KoinComponent
