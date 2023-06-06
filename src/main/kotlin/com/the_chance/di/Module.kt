package com.the_chance.di

import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.datasource.*
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.repository.HoneyMartRepositoryImp
import com.thechance.core.data.usecase.category.*
import com.thechance.core.data.usecase.market.*
import com.thechance.core.data.usecase.product.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModules = module {
    single<CategoryDataSource> { CategoryDataSourceImp() }
    single<MarketDataSource> { MarketDataSourceImp() }
    single<ProductDataSource> { ProductDataSourceImp() }
}

val productUseCaseModule = module {
    singleOf(::ProductUseCasesContainer) { bind<ProductUseCasesContainer>() }
    singleOf(::CreateProductUseCase) { bind<CreateProductUseCase>() }
    singleOf(::DeleteProductUseCase) { bind<DeleteProductUseCase>() }
    singleOf(::UpdateProductUseCase) { bind<UpdateProductUseCase>() }
    singleOf(::UpdateProductCategoryUseCase) { bind<UpdateProductCategoryUseCase>() }
    singleOf(::GetCategoriesForProductUseCase) { bind<GetCategoriesForProductUseCase>() }
}

val marketUseCaseModule = module {
    singleOf(::MarketUseCaseContainer) { bind<MarketUseCaseContainer>() }
    singleOf(::CreateMarketUseCase) { bind<CreateMarketUseCase>() }
    singleOf(::DeleteMarketUseCase) { bind<DeleteMarketUseCase>() }
    singleOf(::GetMarketsUseCase) { bind<GetMarketsUseCase>() }
    singleOf(::UpdateMarketUseCase) { bind<UpdateMarketUseCase>() }
    singleOf(::GetCategoriesByMarketIdUseCase) { bind<GetCategoriesByMarketIdUseCase>() }
}

val categoryUseCaseModule = module {
    singleOf(::CategoryUseCasesContainer) { bind<CategoryUseCasesContainer>() }
    singleOf(::CreateCategoryUseCase) { bind<CreateCategoryUseCase>() }
    singleOf(::DeleteCategoryUseCase) { bind<DeleteCategoryUseCase>() }
    singleOf(::GetAllCategoriesUseCase) { bind<GetAllCategoriesUseCase>() }
    singleOf(::UpdateCategoryUseCase) { bind<UpdateCategoryUseCase>() }
}

val appModules = module {
    single { CoreDataBase() }
    singleOf(::HoneyMartRepositoryImp) { bind<HoneyMartRepository>() }
    includes(
        dataSourceModules,
        categoryUseCaseModule,
        marketUseCaseModule,
        productUseCaseModule
    )
}

