package com.the_chance.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Long,
    val name: String,
    val image: String,
)
