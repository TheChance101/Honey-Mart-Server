package com.thechance.core.domain.usecase.category

import org.koin.core.component.KoinComponent

data class CategoryUseCasesContainer(
    val createCategoryUseCase: CreateCategoryUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase,
    val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val deleteCategoryUseCase: DeleteCategoryUseCase
) : KoinComponent
