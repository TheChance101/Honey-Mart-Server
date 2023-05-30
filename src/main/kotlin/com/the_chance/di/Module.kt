package com.the_chance.di

import com.example.core.data.*
import com.example.ui.service.CategoryService
import com.example.ui.service.MarketCategoriesService
import com.example.ui.service.MarketService
import com.example.ui.service.ProductService
import org.koin.dsl.module

val myModule = module {
    single<CoreDataBase> { CoreDataBaseImp() }
    single<ProductService> { ProductServiceImp(get()) }
    single<MarketService> { MarketServiceImp(get()) }
    single<CategoryService> { CategoryServiceImp(get()) }
    single<MarketCategoriesService> { MarketCategoriesServiceImp(get()) }
}