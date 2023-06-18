package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductImage(val id: Long, val imageUrl: String)