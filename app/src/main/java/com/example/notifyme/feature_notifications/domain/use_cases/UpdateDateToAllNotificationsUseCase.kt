package com.example.notifyme.feature_notifications.domain.use_cases

import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository

class UpdateDateToAllNotificationsUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(date: Long) {
        notificationRepository.updateDateToAllNotifications(date)
    }
}