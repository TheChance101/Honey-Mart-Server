package com.thechance.core.data.datasource

import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product
import com.thechance.core.data.model.WishList
import com.thechance.core.data.model.WishListItem
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.UserTable
import com.thechance.core.data.tables.WishListTable
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class WishListDataSourceImp : WishListDataSource, KoinComponent {
    override suspend fun createWishList(productId: Long, userId: Long): WishList = dbQuery {
        val newWishList = WishListTable.insert {
            it[this.productId] = productId
            it[this.userId] = userId
        }
        WishList(
            id = newWishList[WishListTable.id].value,
            userId = newWishList[WishListTable.userId].value
        )
    }


    //    override suspend fun getAllWishList(userId: Long): List<WishList> {
//
//    }
//
//    override suspend fun deleteFromWishList(productId: Long): Boolean {
//        TODO("Not yet implemented")
//    }
    override suspend fun isProductInWishList(productId: Long): Boolean {
        return dbQuery {
            WishListTable.select {
                WishListTable.productId eq productId
            }.count() > 0
        }
    }
}