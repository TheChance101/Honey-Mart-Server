package com.thechance.core.data

import com.thechance.api.service.DeleteAllTablesService
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.tables.ProductTable
import org.jetbrains.exposed.sql.deleteAll
import org.koin.core.component.KoinComponent

class DeleteAllTablesServiceServiceImp(database: CoreDataBase) :
    BaseService(database), DeleteAllTablesService, KoinComponent {

    override suspend fun deleteAllTables() {
        dbQuery {
            MarketTable.deleteAll()
            CategoriesTable.deleteAll()
            ProductTable.deleteAll()
        }
    }


}