package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Market(
    val marketId: Long,
    val marketName: String,
)
