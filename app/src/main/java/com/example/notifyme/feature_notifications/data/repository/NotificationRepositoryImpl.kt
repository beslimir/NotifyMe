package com.example.notifyme.feature_notifications.data.repository

import com.example.notifyme.feature_notifications.data.local.NotificationDao
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class NotificationRepositoryImpl(
    private val notificationDao: NotificationDao
): NotificationRepository {

    override fun getAllNotifications(): Flow<List<NotificationItem>> = notificationDao.getAllNotifications()

    override fun getAllShownNotifications(): Flow<List<NotificationItem>> = notificationDao.getAllShownNotifications()

    override suspend fun getNotificationById(id: Int): NotificationItem = notificationDao.getNotificationById(id)

    override suspend fun insertNotification(notificationItem: NotificationItem) {
        notificationDao.insertNotification(notificationItem)
    }

    override suspend fun deleteNotification(notificationItem: NotificationItem) {
        notificationDao.deleteNotification(notificationItem)
    }
}