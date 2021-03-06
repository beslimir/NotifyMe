package com.example.notifyme.feature_notifications.data.remote

import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity

data class NotificationItemDto(
    val content: String,
    val details: List<NotificationDetailsDto>,
    val id: Int,
    val title: String
) {
    fun toNotificationItemEntity(date: Long, color: Int): NotificationItemEntity {
        return NotificationItemEntity(
            id = id,
            title = title,
            content = content,
            details = details.map { it.toNotificationDetails() },
            date = date,
            color = color
        )
    }
}