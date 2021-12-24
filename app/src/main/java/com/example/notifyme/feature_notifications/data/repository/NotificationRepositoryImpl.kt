package com.example.notifyme.feature_notifications.data.repository

import androidx.compose.ui.graphics.toArgb
import com.example.notifyme.feature_notifications.data.local.NotificationDao
import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity
import com.example.notifyme.feature_notifications.domain.model.NotificationDetails
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import com.example.notifyme.feature_notifications.util.NotificationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.NullPointerException

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

    override fun getAllNotificationsUntilDate(date: Long): Flow<List<NotificationItem>> {
        val notifications = notificationDao.getAllNotificationsUntilDate(date).map { list ->
            list.map { entity ->
                entity.toNotificationItem()
            }
        }

        return notifications
    }

    override suspend fun getNotificationById(id: Int): NotificationItem =
        notificationDao.getNotificationById(id).toNotificationItem()

    override suspend fun getNotificationByDate(date: Long): NotificationItem {
        return try {
            notificationDao.getNotificationByDate(date).toNotificationItem()
        } catch (e: NullPointerException) {
            //temporary solution...
            getLastNotification()
        }
    }

    override suspend fun insertNotification(notificationItem: NotificationItem) {
        notificationDao.insertNotification(notificationItem.toNotificationItemEntity())
    }

    override suspend fun updateDateToAllNotifications(date: Long) {
        notificationDao.updateDateToAllNotifications(date)
    }

    override suspend fun deleteNotification(notificationItem: NotificationItem) {
        notificationDao.deleteNotification(notificationItem.toNotificationItemEntity())
    }

    private fun getLastNotification() = NotificationItem(
        content = "This is the last notification",
        details = listOf(
            NotificationDetails(
                author = "beslimir",
                dedicated_to = "a special person",
                icon_type = "fire",
                type = "motivational"
            )
        ),
        id = 9999,
        title = "Title of the last notification",
        date = 4133890800000L,
        color = NotificationItemEntity.notificationItemColors.random().toArgb()
    )
}