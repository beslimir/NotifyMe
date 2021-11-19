package com.example.notifyme.feature_notifications.data.repository

import com.example.notifyme.feature_notifications.data.local.NotificationDao
import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map

class NotificationRepositoryImpl(
    private val notificationDao: NotificationDao
): NotificationRepository {

    override fun getAllNotifications(): Flow<List<NotificationItem>> = flow {
        val notifications = notificationDao.getAllNotifications().map {
            it.toNotificationItem()
        }
        emit(notifications)
    }

    override fun getAllShownNotifications(): Flow<List<NotificationItem>> = flow {
        val notifications = notificationDao.getAllShownNotifications().map {
            it.toNotificationItem()
        }
       emit(notifications)
    }

    override suspend fun getNotificationById(id: Int): NotificationItem = notificationDao.getNotificationById(id).toNotificationItem()

    override suspend fun insertNotification(notificationItem: NotificationItem) {
        notificationDao.insertNotification(notificationItem.toNotificationItemEntity())
    }

    override suspend fun deleteNotification(notificationItem: NotificationItem) {
        notificationDao.deleteNotification(notificationItem.toNotificationItemEntity())
    }
}