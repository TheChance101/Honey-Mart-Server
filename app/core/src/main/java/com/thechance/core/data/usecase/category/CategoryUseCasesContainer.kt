package com.thechance.core.data.usecase.category

import org.koin.core.component.KoinComponent

data class CategoryUseCasesContainer(
    val createCategoryUseCase: CreateCategoryUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase,
    val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase
) : KoinComponent
