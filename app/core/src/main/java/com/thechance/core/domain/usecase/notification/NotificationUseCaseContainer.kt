package com.thechance.core.domain.usecase.notification

import org.koin.core.component.KoinComponent

data class NotificationUseCaseContainer(
    val getUserNotificationHistory: GetUserNotificationHistoryUseCase,
    val getOwnerNotificationHistory: GetOwnerNotificationHistoryUseCase,
    val sendUserNotification: SendUserNotificationUseCase,
    val sendOwnerNotification: SendOwnerNotificationUseCase,
    val updateUserNotificationState: UpdateUserNotificationState,
    val updateOwnerNotificationState: UpdateOwnerNotificationState,
) : KoinComponent