package com.example.notifyme.feature_notifications.domain.use_cases

data class UseCasesWrapper(
    val getAllNotificationsUseCase: GetAllNotificationsUseCase,
    val getAllNotificationsUntilDateUseCase: GetAllNotificationsUntilDateUseCase,
    val getNotificationByIdUseCase: GetNotificationByIdUseCase,
    val getNotificationByDateUseCase: GetNotificationByDateUseCase,
    val insertNotificationUseCase: InsertNotificationUseCase,
    val updateDateToAllNotificationsUseCase: UpdateDateToAllNotificationsUseCase,
    val deleteNotificationUseCase: DeleteNotificationUseCase,
    val getJsonFromFirebaseUseCase: GetJsonFromFirebaseUseCase
)
