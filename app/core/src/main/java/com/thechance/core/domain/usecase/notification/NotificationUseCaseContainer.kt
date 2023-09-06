package com.thechance.core.domain.usecase.notification

import org.koin.core.component.KoinComponent

data class NotificationUseCaseContainer(
    val getUserNotificationHistory: GetUserNotificationHistoryUseCase,
    val getOwnerNotificationHistory: GetOwnerNotificationHistoryUseCase,
    val sendUserNotificationOnOrderState: SendUserNotificationOnOrderStateUseCase,
    val sendOwnerNotification: SendOwnerNotificationUseCase,
    val updateNotificationState: UpdateNotificationState,
) : KoinComponent