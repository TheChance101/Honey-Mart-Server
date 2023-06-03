package com.the_chance.di

import com.thechance.api.service.CategoryService
import com.thechance.api.service.DeleteAllTablesService
import com.thechance.api.service.MarketService
import com.thechance.api.service.ProductService
import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.database.CoreDataBaseImp
import com.thechance.core.data.service.CategoryServiceImp
import com.thechance.core.data.service.DeleteAllTablesServiceServiceImp
import com.thechance.core.data.service.MarketServiceImp
import com.thechance.core.data.service.ProductServiceImp
import com.thechance.core.data.validation.category.CategoryValidation
import com.thechance.core.data.validation.category.CategoryValidationImpl
import com.thechance.core.data.validation.market.MarketValidation
import com.thechance.core.data.validation.market.MarketValidationImpl
import com.thechance.core.data.validation.product.ProductValidation
import com.thechance.core.data.validation.product.ProductValidationImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val myModule = module {
    single<CoreDataBase> { CoreDataBaseImp() }
    singleOf(::ProductServiceImp) { bind<ProductService>() }
    singleOf(::MarketServiceImp) { bind<MarketService>() }
    singleOf(::CategoryServiceImp) { bind<CategoryService>() }
    single<ProductValidation> { ProductValidationImpl() }
    single<MarketValidation> { MarketValidationImpl() }
    single<CategoryValidation> { CategoryValidationImpl() }
    single<DeleteAllTablesService> { DeleteAllTablesServiceServiceImp() }
}