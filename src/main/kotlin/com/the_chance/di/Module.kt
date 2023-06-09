package com.the_chance.di

import com.thechance.core.data.database.*
import com.thechance.core.data.datasource.*
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.repository.HoneyMartRepositoryImp
import com.thechance.core.data.usecase.cart.AddProductToCartUseCase
import com.thechance.core.data.usecase.cart.CartUseCasesContainer
import com.thechance.core.data.usecase.cart.DeleteProductInCartUseCase
import com.thechance.core.data.usecase.cart.GetCartUseCase
import com.thechance.core.data.usecase.category.*
import com.thechance.core.data.usecase.market.*
import com.thechance.core.data.usecase.owner.CreateOwnerUseCase
import com.thechance.core.data.usecase.owner.OwnerUseCaseContainer
import com.thechance.core.data.usecase.product.*
import com.thechance.core.data.usecase.user.CreateUserUseCase
import com.thechance.core.data.usecase.user.UserUseCaseContainer
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModules = module {
    single<CategoryDataSource> { CategoryDataSourceImp() }
    single<MarketDataSource> { MarketDataSourceImp() }
    single<ProductDataSource> { ProductDataSourceImp() }
    single<UserDataSource> { UserDataSourceImp() }
    single<OwnerDataSource> { OwnerDataSourceImp() }
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
val userUseCaseModule = module {
    singleOf(::UserUseCaseContainer) { bind<UserUseCaseContainer>() }
    singleOf(::CreateUserUseCase) { bind<CreateUserUseCase>() }
}
val ownerUseCaseModule = module {
    singleOf(::OwnerUseCaseContainer) { bind<OwnerUseCaseContainer>() }
    singleOf(::CreateOwnerUseCase) { bind<CreateOwnerUseCase>() }
}

val cartUseCase = module {
    singleOf(::GetCartUseCase) { bind<GetCartUseCase>() }
    singleOf(::AddProductToCartUseCase) { bind<AddProductToCartUseCase>() }
    singleOf(::DeleteProductInCartUseCase) { bind<DeleteProductInCartUseCase>() }
    singleOf(::CartUseCasesContainer) { bind<CartUseCasesContainer>() }
}

val appModules = module {
    single { CoreDataBase() }
    singleOf(::HoneyMartRepositoryImp) { bind<HoneyMartRepository>() }
    includes(
        cartUseCase,
        dataSourceModules,
        categoryUseCaseModule,
        marketUseCaseModule,
        productUseCaseModule,
        userUseCaseModule,
        ownerUseCaseModule,
    )
}

