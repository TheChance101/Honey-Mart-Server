package com.the_chance.di

import com.thechance.api.service.CategoryService
import com.thechance.api.service.MarketCategoriesService
import com.thechance.api.service.MarketService
import com.thechance.api.service.ProductService
import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.database.CoreDataBaseImp
import com.thechance.core.data.service.CategoryServiceImp
import com.thechance.core.data.service.MarketCategoriesServiceImp
import com.thechance.core.data.service.MarketServiceImp
import com.thechance.core.data.service.ProductServiceImp
import com.thechance.core.data.validation.category.CategoryValidation
import com.thechance.core.data.validation.category.CategoryValidationImpl
import com.thechance.core.data.validation.product.ProductValidation
import com.thechance.core.data.validation.product.ProductValidationImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val myModule = module {
    single<CoreDataBase> { CoreDataBaseImp() }
    singleOf(::ProductServiceImp){bind<ProductService>()} // good job ya noor
    single<MarketService> { MarketServiceImp() }
    single<CategoryService> { CategoryServiceImp() }
    single<MarketCategoriesService> { MarketCategoriesServiceImp() }

    single<ProductValidation> { ProductValidationImpl() }
    single<CategoryValidation> { CategoryValidationImpl() }
}