package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.Admin
import com.thechance.core.entity.market.Market

interface AdminDataSource {
    suspend fun getAdminByEmail(email: String): Admin
    suspend fun isValidAdmin( password: String, email: String): Boolean
    suspend fun getUnApprovedMarkets(): List<Market>
    suspend fun approveMarket(marketId: Long, isApproved: Boolean): Boolean
}