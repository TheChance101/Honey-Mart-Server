package com.thechance.core.data.service

import com.thechance.api.service.DeleteAllTablesService
import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.service.BaseService
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.tables.ProductTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteAllTablesServiceServiceImp :
    BaseService(), DeleteAllTablesService, KoinComponent {
    private val database by inject<CoreDataBase>()

    override suspend fun deleteAllTables() {
        transaction(database.getDatabaseInstance()) {
            SchemaUtils.drop(*arrayOf(MarketTable, CategoriesTable, ProductTable))
        }
    }

}