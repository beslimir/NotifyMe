package com.example.notifyme.feature_notifications.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notifyme.feature_notifications.domain.model.NotificationDetails
import com.example.notifyme.feature_notifications.domain.model.NotificationItem

@Entity
data class NotificationItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String,
    val details: List<NotificationDetails>,
    val isShown: Boolean
) {
    fun toNotificationItem(): NotificationItem {
        return NotificationItem(
            id = id,
            title = title,
            content = content,
            details = details,
            isShown = isShown
        )
    }
}
