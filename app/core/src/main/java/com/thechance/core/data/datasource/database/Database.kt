package com.thechance.core.data.datasource.database

import com.thechance.core.data.datasource.database.tables.*
import com.thechance.core.data.datasource.database.tables.cart.CartProductTable
import com.thechance.core.data.datasource.database.tables.cart.CartTable
import com.thechance.core.data.datasource.database.tables.category.CategoriesTable
import com.thechance.core.data.datasource.database.tables.category.CategoryProductTable
import com.thechance.core.data.datasource.database.tables.coupon.CouponTable
import com.thechance.core.data.datasource.database.tables.coupon.CouponUserTable
import com.thechance.core.data.datasource.database.tables.notification.OwnerDeviceTokenTable
import com.thechance.core.data.datasource.database.tables.notification.OwnerNotificationHistoryTable
import com.thechance.core.data.datasource.database.tables.notification.UserDeviceTokenTable
import com.thechance.core.data.datasource.database.tables.notification.UserNotificationHistoryTable
import com.thechance.core.data.datasource.database.tables.order.OrderProductTable
import com.thechance.core.data.datasource.database.tables.order.OrderTable
import com.thechance.core.data.datasource.database.tables.product.GalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductGalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.data.datasource.database.tables.wishlist.WishListProductTable
import com.thechance.core.data.datasource.database.tables.wishlist.WishListTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class CoreDataBase {

    private val tables by lazy {
        arrayOf(
            CategoriesTable,
            ProductTable,
            CategoryProductTable,
            NormalUserTable,
            OwnerTable,
            CartTable,
            OrderProductTable,
            CartProductTable,
            WishListProductTable,
            WishListTable,
            OrderTable,
            MarketTable,
            GalleryTable,
            ProductGalleryTable,
            CouponTable,
            CouponUserTable,
            UserDeviceTokenTable,
            UserNotificationHistoryTable,
            OwnerDeviceTokenTable,
            OwnerNotificationHistoryTable,
            ProductReviewTable
        )
    }

    init {
        createTables()
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

    private fun createTables() {
        transaction(getDatabaseInstance()) {
            tables.forEach {
                SchemaUtils.create(it)
            }
        }
    }

    fun deleteAllTables() {
        transaction(getDatabaseInstance()) {
            SchemaUtils.drop(*tables)
        }
        createTables()
    }
}