package com.example.notifyme.feature_notifications.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notifyme.feature_notifications.domain.model.NotificationDetails
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.presentation.ui.theme.*

@Entity
data class NotificationItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String,
    val details: List<NotificationDetails>,
    var date: Long,
    var color: Int
) {

    companion object {
        val notificationItemColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }

    fun toNotificationItem(): NotificationItem {
        return NotificationItem(
            id = id,
            title = title,
            content = content,
            details = details,
            date = date,
            color = color
        )
    }
}
