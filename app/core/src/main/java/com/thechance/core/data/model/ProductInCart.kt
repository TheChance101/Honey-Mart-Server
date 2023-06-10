package com.thechance.core.data.model

import kotlinx.serialization.Serializable


@Serializable
data class ProductInCart(val id: Long, val name: String, val count: Int, val price: Double)

