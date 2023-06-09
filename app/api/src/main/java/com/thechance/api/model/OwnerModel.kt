package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class OwnerModel(val ownerId: Long, val fullName: String, val email: String)
