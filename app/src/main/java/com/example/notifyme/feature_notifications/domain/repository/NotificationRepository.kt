package com.example.notifyme.feature_notifications.domain.repository

import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun getAllNotifications(): Flow<List<NotificationItem>>
    fun getAllNotificationsUntilDate(date: Long): Flow<List<NotificationItem>>
    suspend fun getNotificationById(id: Int): NotificationItem
    suspend fun getNotificationByDate(date: Long): NotificationItem
    suspend fun insertNotification(notificationItem: NotificationItem)
    suspend fun updateDateToAllNotifications(date: Long)
    suspend fun deleteNotification(notificationItem: NotificationItem)

}