package com.example.notifyme.feature_notifications.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM NotificationItemEntity")
    suspend fun getAllNotifications(): List<NotificationItemEntity>

    @Query("SELECT * FROM NotificationItemEntity WHERE isShown = '1'")
    suspend fun getAllShownNotifications(): List<NotificationItemEntity>

    @Query("SELECT * FROM NotificationItemEntity WHERE id = :id")
    suspend fun getNotificationById(id: Int): NotificationItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notificationItem: NotificationItemEntity)

    @Delete
    suspend fun deleteNotification(notificationItem: NotificationItemEntity)

}