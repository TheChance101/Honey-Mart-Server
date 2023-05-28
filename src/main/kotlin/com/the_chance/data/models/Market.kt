package com.the_chance.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Market(
    val marketId: Long,
    val marketName: String,
)
