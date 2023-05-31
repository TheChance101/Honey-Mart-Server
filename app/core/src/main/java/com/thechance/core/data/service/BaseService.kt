package com.thechance.core.data.service

import com.thechance.core.data.database.CoreDataBase
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
abstract class BaseService(vararg table: Table): KoinComponent {

    private val database by inject<CoreDataBase>()
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