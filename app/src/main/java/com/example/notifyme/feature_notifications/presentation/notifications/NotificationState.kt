package com.example.notifyme.feature_notifications.presentation.notifications

import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.util.OrderType

data class NotificationState(
    val notifications: List<NotificationItem> = emptyList(),
    val orderType: OrderType = OrderType.Ascending,
    val isOrderSectionVisible: Boolean = false
)