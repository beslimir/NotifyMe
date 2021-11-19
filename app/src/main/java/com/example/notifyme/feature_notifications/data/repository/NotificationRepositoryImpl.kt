package com.example.notifyme.feature_notifications.data.repository

import com.example.notifyme.feature_notifications.data.local.NotificationDao
import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map

/**
 * getAllNotifications() is a Flow (non-suspend) - map twice; return
 * getAllShownNotifications is a List (suspend) - map once; flow - emit
 *
 * **/

class NotificationRepositoryImpl(
    private val notificationDao: NotificationDao
): NotificationRepository {

    override fun getAllNotifications(): Flow<List<NotificationItem>> {
        val notifications = notificationDao.getAllNotifications().map { list ->
            list.map { entity ->
                entity.toNotificationItem()
            }
        }

        return notifications
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