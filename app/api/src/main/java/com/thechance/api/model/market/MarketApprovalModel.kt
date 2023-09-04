package com.thechance.api.model.market

import kotlinx.serialization.Serializable

@Serializable
data class MarketApprovalModel(
    val isMarketApproved: Boolean,
    val marketId: Long
)
