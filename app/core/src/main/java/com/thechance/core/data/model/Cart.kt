package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Cart(val products: List<ProductInCart>, val total: Double)

