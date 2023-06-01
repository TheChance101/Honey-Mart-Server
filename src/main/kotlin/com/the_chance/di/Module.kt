package com.the_chance.di

import com.thechance.api.service.CategoryService
import com.thechance.api.service.MarketCategoriesService
import com.thechance.api.service.MarketService
import com.thechance.api.service.ProductService
import com.thechance.core.data.*
import org.koin.dsl.module

val myModule = module {
    single<CoreDataBase> { CoreDataBaseImp() }
    singleOf(::ProductServiceImp){bind<ProductService>()} // good job ya noor
    single<MarketService> { MarketServiceImp() }
    single<CategoryService> { CategoryServiceImp() }
    single<MarketCategoriesService> { MarketCategoriesServiceImp() }
    single<ProductValidation> { ProductValidationImpl() }
    single<CategoryValidation> { CategoryValidationImpl() }
    single<DeleteAllTablesService> { DeleteAllTablesServiceServiceImp(get()) }
}