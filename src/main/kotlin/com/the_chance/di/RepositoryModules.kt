package com.the_chance.di

import com.thechance.core.data.repository.AuthRepositoryImp
import com.thechance.core.data.repository.HoneyMartRepositoryImp
import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.domain.repository.HoneyMartRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoriesModules = module {
    singleOf(::HoneyMartRepositoryImp) { bind<HoneyMartRepository>() }
    singleOf(::AuthRepositoryImp) { bind<AuthRepository>() }
}