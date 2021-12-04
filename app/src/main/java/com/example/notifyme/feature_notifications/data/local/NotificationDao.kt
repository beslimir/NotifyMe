package com.example.notifyme.feature_notifications.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * getAllNotifications() is a Flow (non-suspend)
 * getAllShownNotifications is a List (suspend)
 *
 * **/

@Dao
interface NotificationDao {

    @Query("SELECT * FROM NotificationItemEntity")
    fun getAllNotifications(): Flow<List<NotificationItemEntity>>

    //TODO: Show notifications until date and hour (if message is not yet arrived via notification)
    @Query("SELECT * FROM NotificationItemEntity WHERE date <= :date")
    suspend fun getAllNotificationsUntilDate(date: Long): List<NotificationItemEntity>

    @Query("SELECT * FROM NotificationItemEntity WHERE id = :id")
    suspend fun getNotificationById(id: Int): NotificationItemEntity

    @Query("SELECT * FROM NotificationItemEntity WHERE date = :date")
    suspend fun getNotificationByDate(date: Long): NotificationItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notificationItem: NotificationItemEntity)

    @Delete
    suspend fun deleteNotification(notificationItem: NotificationItemEntity)

}