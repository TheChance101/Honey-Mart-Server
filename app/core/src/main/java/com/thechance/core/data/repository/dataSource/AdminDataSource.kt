package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.Admin
import com.thechance.core.entity.market.MarketRequest

interface AdminDataSource {
    suspend fun getAdminByEmail(email: String): Admin
    suspend fun isValidAdmin( password: String, email: String): Boolean
    suspend fun getUnApprovedMarketsDetails(): List<MarketRequest>
    suspend fun approveMarket(marketId: Long, isApproved: Boolean): Boolean
}