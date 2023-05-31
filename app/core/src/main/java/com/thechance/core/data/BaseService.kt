package com.thechance.core.data

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

abstract class BaseService(database: CoreDataBase, vararg table: Table) {
    init {
        transaction(database.getDatabaseInstance()) {
            table.forEach {
                SchemaUtils.create(it)
            }
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}