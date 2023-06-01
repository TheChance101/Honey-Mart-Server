package com.the_chance.di

import com.thechance.api.service.*
import com.thechance.core.data.*
import org.koin.dsl.module

val myModule = module {
    single<CoreDataBase> { CoreDataBaseImp() }
    single<ProductService> { ProductServiceImp(get()) }
    single<MarketService> { MarketServiceImp(get()) }
    single<CategoryService> { CategoryServiceImp(get()) }
    single<MarketCategoriesService> { MarketCategoriesServiceImp(get()) }
    single<DeleteAllTablesService> { DeleteAllTablesServiceServiceImp(get()) }
}