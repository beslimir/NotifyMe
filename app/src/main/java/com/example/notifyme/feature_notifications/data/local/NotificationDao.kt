package com.example.notifyme.feature_notifications.data.local

import androidx.room.*
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM NotificationItemEntity")
    fun getAllNotifications(): Flow<List<NotificationItem>>

    @Query("SELECT * FROM NotificationItemEntity WHERE isShown = 'true'")
    fun getAllShownNotifications(): Flow<List<NotificationItem>>

    @Query("SELECT * FROM NotificationItemEntity WHERE id = :id")
    suspend fun getNotificationById(id: Int): NotificationItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notificationItem: NotificationItem)

    @Delete
    suspend fun deleteNotification(notificationItem: NotificationItem)

}