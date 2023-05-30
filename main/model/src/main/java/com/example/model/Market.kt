package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Market(
    val marketId: Long,
    val marketName: String,
)
