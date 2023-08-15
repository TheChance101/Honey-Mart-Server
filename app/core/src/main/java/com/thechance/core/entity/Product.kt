package com.thechance.core.entity

data class Product(
    val id: Long,
    val name: String,
    val quantity: String?,
    val price: Double,
    val image: List<Image>,
    val marketId: Long
)
