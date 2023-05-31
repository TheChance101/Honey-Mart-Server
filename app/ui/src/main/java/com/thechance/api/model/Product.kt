package com.thechance.api.model

import kotlinx.serialization.Serializable

/**
 * TODO add Currency
 * */
@Serializable
data class Product(
    val id: Long,
    val name: String,
    val quantity: String?,
    val price: Double,
)