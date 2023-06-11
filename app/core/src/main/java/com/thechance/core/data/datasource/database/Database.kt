package com.thechance.core.data.datasource.database

import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.datasource.database.tables.NormalUserTable
import com.thechance.core.data.datasource.database.tables.OwnerTable
import com.thechance.core.data.datasource.database.tables.ProductTable
import com.thechance.core.data.datasource.database.tables.cart.CartProductTable
import com.thechance.core.data.datasource.database.tables.cart.CartTable
import com.thechance.core.data.datasource.database.tables.category.CategoriesTable
import com.thechance.core.data.datasource.database.tables.category.CategoryProductTable
import com.thechance.core.data.datasource.database.tables.order.OrderMarketTable
import com.thechance.core.data.datasource.database.tables.order.OrderProductTable
import com.thechance.core.data.datasource.database.tables.order.OrderTable
import com.thechance.core.data.datasource.database.tables.wishlist.WishListProductTable
import com.thechance.core.data.datasource.database.tables.wishlist.WishListTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class CoreDataBase {

    private val tables by lazy {
        listOf(
            MarketTable,
            CategoriesTable,
            ProductTable,
            CategoryProductTable,
            NormalUserTable,
            OwnerTable,
            CartTable,
            CartProductTable,
            OrderTable,
            OrderProductTable,
            OrderMarketTable,
            CartProductTable,
            WishListTable,
            WishListProductTable
        )
    }

    init {
        transaction(getDatabaseInstance()) {
            tables.forEach {
                SchemaUtils.create(it)
            }
        }
    }

    private fun getDatabaseInstance(): Database {
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

    fun deleteAllTables() {
        transaction(getDatabaseInstance()) {
            SchemaUtils.drop(
                *arrayOf(
                    MarketTable,
                    CategoriesTable,
                    ProductTable,
                    CategoryProductTable,
                    NormalUserTable,
                    OwnerTable,
                    CartTable,
                    CartProductTable,
                    OrderTable,
                    OrderProductTable,
                    OrderMarketTable,
                    CartProductTable,
                    WishListTable,
                    WishListProductTable
                )
            )
        }
    }
}