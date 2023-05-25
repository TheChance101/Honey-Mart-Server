package com.the_chance.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Market(
    val id: Int,
    val name: String,
)
