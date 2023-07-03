package com.thechance.core.data.security.principal

import io.ktor.server.auth.*

data class AppPrincipal(val key: String) : Principal

