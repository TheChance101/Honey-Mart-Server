package com.the_chance.data.services

import com.the_chance.data.tables.CategoriesTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

abstract class BaseService(database: Database) {

    init {
        transaction(database) {
            SchemaUtils.create(CategoriesTable)
        }
    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}