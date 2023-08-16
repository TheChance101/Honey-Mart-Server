package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.datasource.mapper.toMarket
import com.thechance.core.data.repository.dataSource.AdminDataSource
import com.thechance.core.entity.Admin
import com.thechance.core.entity.market.Market
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class AdminDataSourceImp : AdminDataSource, KoinComponent {

    override suspend fun getAdminByEmail(email: String): Admin {
        return Admin(
            adminId = 1,
            email = System.getenv("adminEmail"),
            fullName = System.getenv("adminFullName"),
            password = System.getenv("adminPassword"),
            salt = ""
        )
    }

    override suspend fun isValidAdmin(password: String, email: String): Boolean {
        return getAdminByEmail(email).let {
            it.password == password && it.email == email
        }
    }

    override suspend fun getUnApprovedMarkets(): List<Market> {
        return dbQuery {
            MarketTable.select {
                (MarketTable.isDeleted eq false) and (MarketTable.isApproved eq false)
            }.map { it.toMarket() }.toList()
        }
    }

    override suspend fun approveMarket(marketId: Long, isApproved: Boolean): Boolean = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) { marketRow ->
            if (isApproved) {
                marketRow[this.isApproved] = true
            } else {
                marketRow[this.isDeleted] = true
            }
        }
        true
    }

}