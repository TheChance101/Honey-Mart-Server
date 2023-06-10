package com.thechance.core.domain.usecase

import com.thechance.core.data.datasource.database.CoreDataBase

class DeleteAllTablesUseCase(private val coreDataBase: CoreDataBase) {

    operator fun invoke() {
        coreDataBase.deleteAllTables()
    }
}