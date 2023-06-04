package com.thechance.core.data.service

import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.tables.ProductTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteAllTablesService : BaseService(), KoinComponent {

    private val database by inject<CoreDataBase>()

    suspend fun deleteAllTables() {
        transaction(database.getDatabaseInstance()) {
            SchemaUtils.drop(*arrayOf(MarketTable, CategoriesTable, ProductTable, CategoryProductTable))
        }
    }

}