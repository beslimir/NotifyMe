package com.example.notifyme.feature_notifications.domain.use_cases

import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository

class InsertNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(notificationItem: NotificationItem) {
        notificationRepository.insertNotification(notificationItem)
    }
}