package com.thechance.core.domain.usecase.admin

import com.thechance.core.utils.ADMIN_ROLE
import com.thechance.core.utils.AdminAccessDeniedException
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class AuthenticateAdminUseCase : KoinComponent {

    operator fun invoke(role: String?) {
        if (!isValidRole(ADMIN_ROLE, role)) {
            throw AdminAccessDeniedException()
        }
    }
}