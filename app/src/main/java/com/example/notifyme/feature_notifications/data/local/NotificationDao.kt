package com.example.notifyme.feature_notifications.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM NotificationItemEntity")
    fun getAllNotifications(): Flow<List<NotificationItemEntity>>

    @Query("SELECT * FROM NotificationItemEntity WHERE isShown = 'true'")
    fun getAllShownNotifications(): Flow<List<NotificationItemEntity>>

    @Query("SELECT * FROM NotificationItemEntity WHERE id = :id")
    suspend fun getNotificationById(id: Int): NotificationItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notificationItem: NotificationItemEntity)

    @Delete
    suspend fun deleteNotification(notificationItem: NotificationItemEntity)

}