package com.thechance.core.domain.usecase.notification

import org.koin.core.component.KoinComponent

data class NotificationUseCaseContainer(
    val getNotificationHistoryUseCase: GetNotificationHistoryUseCase,
    val sendNotificationOnOrderStateUseCase: SendNotificationOnOrderStateUseCase,
    val updateNotificationState: UpdateNotificationState,
) : KoinComponent