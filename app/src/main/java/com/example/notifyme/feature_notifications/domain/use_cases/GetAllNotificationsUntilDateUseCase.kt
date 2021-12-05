package com.example.notifyme.feature_notifications.domain.use_cases

import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import com.example.notifyme.feature_notifications.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotificationsUntilDateUseCase(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(orderType: OrderType, date: Long): Flow<List<NotificationItem>> {
        return when (orderType) {
            is OrderType.Ascending -> notificationRepository.getAllNotificationsUntilDate(date).map { notifications ->
                notifications.sortedBy { it.id }
            }
            is OrderType.Descending -> notificationRepository.getAllNotificationsUntilDate(date).map { notifications ->
                notifications.sortedByDescending { it.id }
            }
        }
    }
}