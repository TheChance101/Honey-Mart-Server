package com.the_chance.di

import com.thechance.api.service.CategoryService
import com.thechance.api.service.MarketCategoriesService
import com.thechance.api.service.MarketService
import com.thechance.api.service.ProductService
import com.thechance.core.data.*
import com.thechance.core.data.validation.category.CategoryValidation
import com.thechance.core.data.validation.category.CategoryValidationImpl
import com.thechance.core.data.validation.product.ProductValidation
import com.thechance.core.data.validation.product.ProductValidationImpl
import org.koin.dsl.module

val myModule = module {
    single<CoreDataBase> { CoreDataBaseImp() }
    single<ProductService> { ProductServiceImp(get(), get()) }
    single<MarketService> { MarketServiceImp(get()) }
    single<CategoryService> { CategoryServiceImp(get()) }
    single<MarketCategoriesService> { MarketCategoriesServiceImp(get()) }

    single<ProductValidation> { ProductValidationImpl() }
    single<CategoryValidation> { CategoryValidationImpl() }
}