package com.example.notifyme.feature_notifications.domain.use_cases

import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotificationsUseCase(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(): Flow<List<NotificationItem>> {
        return notificationRepository.getAllNotifications().map { notifications ->
            notifications.sortedBy { it.id }
        }
    }
}