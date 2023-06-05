package com.the_chance.di

import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.service.DeleteAllTablesService
import com.thechance.core.data.service.MarketService
import com.thechance.core.data.service.ProductService
import com.thechance.core.data.usecase.category.*
import com.thechance.core.data.usecase.market.*
import com.thechance.core.data.usecase.product.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val myModule = module {
    single { CoreDataBase() }
    singleOf(::ProductService) { bind<ProductService>() }
    singleOf(::MarketService) { bind<MarketService>() }
    singleOf(::CategoryService) { bind<CategoryService>() }
    singleOf(::DeleteAllTablesService) { bind<DeleteAllTablesService>() }

    singleOf(::CategoryUseCasesContainer) { bind<CategoryUseCasesContainer>() }
    singleOf(::CreateCategoryUseCase) { bind<CreateCategoryUseCase>() }
    singleOf(::DeleteCategoryUseCase) { bind<DeleteCategoryUseCase>() }
    singleOf(::GetAllCategoriesUseCase) { bind<GetAllCategoriesUseCase>() }
    singleOf(::UpdateCategoryUseCase) { bind<UpdateCategoryUseCase>() }

    singleOf(::MarketUseCaseContainer) { bind<MarketUseCaseContainer>() }
    singleOf(::CreateMarketUseCase) { bind<CreateMarketUseCase>() }
    singleOf(::DeleteMarketUseCase) { bind<DeleteMarketUseCase>() }
    singleOf(::GetMarketsUseCase) { bind<GetMarketsUseCase>() }
    singleOf(::UpdateMarketUseCase) { bind<UpdateMarketUseCase>() }
    singleOf(::GetCategoriesByMarketIdUseCase) { bind<GetCategoriesByMarketIdUseCase>() }

    singleOf(::ProductUseCasesContainer) { bind<ProductUseCasesContainer>() }
    singleOf(::CreateProductUseCase) { bind<CreateProductUseCase>() }
    singleOf(::DeleteProductUseCase) { bind<DeleteProductUseCase>() }
    singleOf(::UpdateProductUseCase) { bind<UpdateProductUseCase>() }
    singleOf(::UpdateProductCategoryUseCase) { bind<UpdateProductCategoryUseCase>() }
    singleOf(::GetCategoriesForProductUseCase) { bind<GetCategoriesForProductUseCase>() }

}