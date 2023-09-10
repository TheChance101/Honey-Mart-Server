package com.the_chance.di

import com.thechance.core.data.datasource.*
import com.thechance.core.data.datasource.ReviewDataSourceImp
import com.thechance.core.data.repository.dataSource.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModules = module {
    single<CategoryDataSource> { CategoryDataSourceImp() }
    single<MarketDataSource> { MarketDataSourceImp() }
    single<ProductDataSource> { ProductDataSourceImp() }
    single<UserDataSource> { UserDataSourceImp() }
    single<OwnerDataSource> { OwnerDataSourceImp() }
    single<AdminDataSource> { AdminDataSourceImp() }
    single<OrderDataSource> { OrderDataSourceImp() }
    singleOf(::NotificationDataSourceImp) { bind<NotificationDataSource>() }
    single<DeviceTokenDataSource> { DeviceTokenDataSourceImp() }
    single<CouponDataSource> { CouponDataSourceImp() }
    single<ReviewDataSource> { ReviewDataSourceImp() }
}
