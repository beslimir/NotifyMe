package com.example.notifyme.feature_notifications.domain.use_cases

data class UseCasesWrapper(
    val getAllNotificationsUseCase: GetAllNotificationsUseCase,
    val getAllShownNotificationsUseCase: GetAllShownNotificationsUseCase,
    val getNotificationByIdUseCase: GetNotificationByIdUseCase,
    val insertNotificationUseCase: InsertNotificationUseCase,
    val deleteNotificationUseCase: DeleteNotificationUseCase
)
