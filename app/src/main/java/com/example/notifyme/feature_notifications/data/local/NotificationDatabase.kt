package com.example.notifyme.feature_notifications.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [NotificationItemEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NotificationDatabase: RoomDatabase() {

    abstract val notificationDao: NotificationDao

}