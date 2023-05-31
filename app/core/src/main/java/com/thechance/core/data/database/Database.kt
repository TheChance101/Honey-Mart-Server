package com.thechance.core.data.database

import org.jetbrains.exposed.sql.Database

interface CoreDataBase {
    fun getDatabaseInstance(): Database
}

class CoreDataBaseImp : CoreDataBase {
    override fun getDatabaseInstance(): Database {
        val host = System.getenv("host")
        val port = System.getenv("port")
        val databaseName = System.getenv("databaseName")
        val databaseUsername = System.getenv("databaseUsername")
        val databasePassword = System.getenv("databasePassword")

        return Database.connect(
            "jdbc:postgresql://$host:$port/$databaseName",
            driver = "org.postgresql.Driver",
            user = databaseUsername,
            password = databasePassword
        )
    }

}