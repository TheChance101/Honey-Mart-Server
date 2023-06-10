package com.the_chance.di

import com.thechance.core.data.usecase.repository.AuthRepository
import com.thechance.core.data.repository.AuthRepositoryImp
import com.thechance.core.data.usecase.repository.HoneyMartRepository
import com.thechance.core.data.repository.HoneyMartRepositoryImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoriesModules = module {
    singleOf(::HoneyMartRepositoryImp) { bind<HoneyMartRepository>() }
    singleOf(::AuthRepositoryImp) { bind<AuthRepository>() }
}