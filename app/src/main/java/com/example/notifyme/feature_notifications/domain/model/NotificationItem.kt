package com.example.notifyme.feature_notifications.domain.model

import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity
import com.example.notifyme.feature_notifications.presentation.ui.theme.*

data class NotificationItem(
    val content: String,
    val details: List<NotificationDetails>,
    val id: Int,
    val title: String,
    var date: Long,
    var color: Int
) {

    fun toNotificationItemEntity(): NotificationItemEntity {
        return NotificationItemEntity(
            id = id,
            title = title,
            content = content,
            details = details,
            date = date,
            color = color
        )
    }
}