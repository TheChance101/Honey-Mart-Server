package com.the_chance.di

import com.thechance.core.data.datasource.*
import com.thechance.core.data.repository.dataSource.*
import org.koin.dsl.module

val dataSourceModules = module {
    single<CategoryDataSource> { CategoryDataSourceImp() }
    single<MarketDataSource> { MarketDataSourceImp() }
    single<ProductDataSource> { ProductDataSourceImp() }
    single<UserDataSource> { UserDataSourceImp() }
    single<OwnerDataSource> { OwnerDataSourceImp() }
    single<OrderDataSource> { OrderDataSourceImp() }
}
