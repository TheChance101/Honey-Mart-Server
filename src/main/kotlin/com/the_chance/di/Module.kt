package com.the_chance.di

import com.example.core.data.CategoryServiceImp
import com.example.core.data.MarketCategoriesServiceImp
import com.example.core.data.MarketServiceImp
import com.example.core.data.ProductServiceImp
import com.example.ui.service.CategoryService
import com.example.ui.service.MarketCategoriesService
import com.example.ui.service.MarketService
import com.example.ui.service.ProductService
import com.the_chance.getDatabaseInstance
import org.koin.dsl.module

val myModule = module {
    single<ProductService> { ProductServiceImp(get()) }
    single <MarketService>{ MarketServiceImp(get()) }
    single <CategoryService>{ CategoryServiceImp(get()) }
    single <MarketCategoriesService>{ MarketCategoriesServiceImp(get()) }
    single { getDatabaseInstance() }
}