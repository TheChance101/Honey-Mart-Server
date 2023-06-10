package com.the_chance.di

import com.thechance.core.data.datasource.database.CoreDataBase
import com.thechance.core.data.security.hashing.HashingService
import com.thechance.core.data.security.hashing.SHA256HashingService
import com.thechance.core.data.security.token.JwtTokenService
import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.data.security.token.TokenService
import com.thechance.core.domain.usecase.DeleteAllTablesUseCase
import io.ktor.server.config.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


val appModules = module {
    single { CoreDataBase() }
    single<TokenService> { JwtTokenService() }
    single<HashingService> { SHA256HashingService() }

    single<TokenConfig> {
        TokenConfig(
            issuer = ApplicationConfig("jwt.issuer").toString(),
            audience = ApplicationConfig("jwt.audience").toString(),
            expiresIn = TimeUnit.HOURS.toMillis(3),
            secret = System.getenv("HONEY_JWT_SECRET")
        )
    }

    singleOf(::DeleteAllTablesUseCase) { bind<com.thechance.core.domain.usecase.DeleteAllTablesUseCase>() }

    includes(
        cartUseCase,
        dataSourceModules,
        categoryUseCaseModule,
        marketUseCaseModule,
        productUseCaseModule,
        wishListUseCaseModule,
        orderUseCaseModule,
        userUseCaseModule,
        ownerUseCaseModule,
        repositoriesModules
    )
}

