package com.the_chance.di

import com.the_chance.data.services.*
import com.the_chance.getDatabaseInstance
import org.koin.dsl.module

val myModule = module {
    single<ProductService> { ProductServiceImp(get()) }
    single <MarketService>{ MarketServiceImp(get()) }
    single <CategoryService>{ CategoryServiceImp(get()) }
    single <MarketCategoriesService>{ MarketCategoriesServiceImp(get()) }
    factory { getDatabaseInstance() }
}