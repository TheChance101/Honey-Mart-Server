package com.thechance.core.data.usecase

import com.thechance.core.data.database.CoreDataBase

class DeleteAllTablesUseCase(private val coreDataBase: CoreDataBase) {

    operator fun invoke() {
        coreDataBase.deleteAllTables()
    }
}