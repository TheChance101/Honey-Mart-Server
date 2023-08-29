package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.datasource.database.tables.OwnerTable
import com.thechance.core.data.repository.dataSource.AdminDataSource
import com.thechance.core.entity.AdminDetails
import com.thechance.core.entity.OwnerDetails
import com.thechance.core.entity.market.MarketRequest
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class AdminDataSourceImp : AdminDataSource, KoinComponent {

    override suspend fun getAdminByEmail(email: String): AdminDetails {
        return AdminDetails(
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

    override suspend fun getMarketsRequestsDetails(isApproved: Boolean?): List<MarketRequest> {
        return dbQuery {
            (MarketTable innerJoin OwnerTable)
                .select {
                    (MarketTable.isDeleted eq false) and
                            (isApproved?.let { MarketTable.isApproved eq it } ?: Op.TRUE)
                }.map { resultRow ->
                    val ownerId = resultRow[MarketTable.ownerId].value
                    val ownerDetails = getOwnerDetails(ownerId)
                    MarketRequest(
                        marketId = resultRow[MarketTable.id].value,
                        marketName = resultRow[MarketTable.name],
                        imageUrl = resultRow[MarketTable.imageUrl],
                        description = resultRow[MarketTable.description],
                        marketStatus = resultRow[MarketTable.status],
                        isApproved = resultRow[MarketTable.isApproved],
                        address = resultRow[MarketTable.address],
                        ownerName = ownerDetails.ownerName ?: "Unknown Owner",
                        ownerEmail = ownerDetails.ownerEmail ?: "Unknown Owner",
                    )
                }.toList()
        }
    }


    private suspend fun getOwnerDetails(ownerId: Long): OwnerDetails {
        val ownerDetails = dbQuery {
            OwnerTable.select { OwnerTable.id eq ownerId }.singleOrNull()
        }
        val ownerName = ownerDetails?.get(OwnerTable.fullName)
        val ownerEmail = ownerDetails?.get(OwnerTable.email)
        return OwnerDetails(ownerName, ownerEmail)
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