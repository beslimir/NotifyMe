package com.example.notifyme.feature_notifications.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NotificationItemEntity::class],
    version = 1
)
abstract class NotificationDatabase: RoomDatabase() {

    abstract val notificationDao: NotificationDao

}