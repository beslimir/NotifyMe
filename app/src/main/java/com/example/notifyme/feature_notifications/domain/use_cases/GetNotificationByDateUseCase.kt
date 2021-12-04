package com.example.notifyme.feature_notifications.domain.use_cases

import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository

class GetNotificationByDateUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(date: Long): NotificationItem = notificationRepository.getNotificationByDate(date)
}