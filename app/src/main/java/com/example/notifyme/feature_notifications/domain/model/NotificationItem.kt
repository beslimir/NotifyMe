package com.example.notifyme.feature_notifications.domain.model

import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity

data class NotificationItem(
    val content: String,
    val details: List<NotificationDetails>,
    val id: Int,
    val title: String,
    val isShown: Boolean = false
) {
    fun toNotificationItemEntity(): NotificationItemEntity {
        return NotificationItemEntity(
            id = id,
            title = title,
            content = content,
            details = details,
            isShown = false
        )
    }
}