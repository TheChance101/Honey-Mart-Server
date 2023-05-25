package com.the_chance.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val quantity: String?,
    val price: Double,
    val isDeleted: Boolean = false
)