package com.the_chance.di

import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.datasource.*
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.repository.HoneyMartRepositoryImp
import com.thechance.core.data.security.hashing.HashingService
import com.thechance.core.data.security.hashing.SHA256HashingService
import com.thechance.core.data.security.token.JwtTokenService
import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.data.security.token.TokenService
import com.thechance.core.data.usecase.category.*
import com.thechance.core.data.usecase.market.*
import com.thechance.core.data.usecase.order.CancelOrderUseCase
import com.thechance.core.data.usecase.order.CreateOrderUseCase
import com.thechance.core.data.usecase.order.GetOrdersForMarketUseCase
import com.thechance.core.data.usecase.order.OrderUseCasesContainer
import com.thechance.core.data.usecase.product.*
import io.ktor.server.config.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val dataSourceModules = module {
    single<CategoryDataSource> { CategoryDataSourceImp() }
    single<MarketDataSource> { MarketDataSourceImp() }
    single<ProductDataSource> { ProductDataSourceImp() }
    single<UserDataSource> { UserDataSourceImp() }
    single<OwnerDataSource> { OwnerDataSourceImp() }
    single<OrderDataSource> { OrderDataSourceImp() }
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

val orderUseCaseModule = module {
    singleOf(::OrderUseCasesContainer) { bind<OrderUseCasesContainer>() }
    singleOf(::CreateOrderUseCase) { bind<CreateOrderUseCase>() }
    singleOf(::GetOrdersForMarketUseCase) { bind<GetOrdersForMarketUseCase>() }
    singleOf(::CancelOrderUseCase) { bind<CancelOrderUseCase>() }
}

val appModules = module {
    single { CoreDataBase() }
    single<TokenService> { JwtTokenService() }
    single<HashingService> { SHA256HashingService() }

    single<TokenConfig> {
        TokenConfig(
            issuer = ApplicationConfig("jwt.issuer").toString(),
            audience = ApplicationConfig("jwt.audience").toString(),
            expiresIn = TimeUnit.HOURS.toMillis(1),
            secret = System.getenv("HONEY_JWT_SECRET")
        )


    }
    singleOf(::HoneyMartRepositoryImp) { bind<HoneyMartRepository>() }
    includes(
        dataSourceModules,
        categoryUseCaseModule,
        marketUseCaseModule,
        productUseCaseModule,
        orderUseCaseModule
        userUseCaseModule,
        ownerUseCaseModule
    )
}

