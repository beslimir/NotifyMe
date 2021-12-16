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

    @Query("SELECT * FROM NotificationItemEntity WHERE date <= :date")
    suspend fun getAllNotificationsUntilDate(date: Long): List<NotificationItemEntity>

    @Query("SELECT * FROM NotificationItemEntity WHERE id = :id")
    suspend fun getNotificationById(id: Int): NotificationItemEntity

    @Query("SELECT * FROM NotificationItemEntity WHERE date = :date")
    suspend fun getNotificationByDate(date: Long): NotificationItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notificationItem: NotificationItemEntity)

    @Query("UPDATE NotificationItemEntity SET date = :date + date")
    suspend fun updateDateToAllNotifications(date: Long)

    @Delete
    suspend fun deleteNotification(notificationItem: NotificationItemEntity)

}