package com.the_chance.di

import com.google.firebase.messaging.FirebaseMessaging
import com.thechance.core.data.datasource.database.CoreDataBase
import com.thechance.core.data.repository.security.HashingService
import com.thechance.core.data.repository.security.TokenService
import com.thechance.core.data.security.hashing.SHA256HashingService
import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.data.security.token.TokenServiceImp
import com.thechance.core.data.security.token.TokenVerifier
import com.thechance.core.domain.usecase.DeleteAllTablesUseCase
import io.ktor.server.config.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


val appModules = module {
    single { CoreDataBase() }
    single { FirebaseMessaging.getInstance() }


    singleOf(::TokenServiceImp) { bind<TokenService>() }
    single<HashingService> { SHA256HashingService() }

    single<TokenConfig> {
        TokenConfig(
            issuer = ApplicationConfig("jwt.issuer").toString(),
            audience = ApplicationConfig("jwt.audience").toString(),
            accessTokenExpiresIn = TimeUnit.DAYS.toMillis(1),
            refreshTokenExpiresIn = TimeUnit.DAYS.toMillis(30),
            secret = System.getenv("HONEY_JWT_SECRET")
        )
    }
    singleOf(::TokenVerifier) { bind<TokenVerifier>() }

    singleOf(::DeleteAllTablesUseCase) { bind<DeleteAllTablesUseCase>() }

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
        adminUseCaseModule,
        repositoriesModules,
        tokenUseCase,
        notificationUseCase,
        deviceTokenUseCase
    )
}

