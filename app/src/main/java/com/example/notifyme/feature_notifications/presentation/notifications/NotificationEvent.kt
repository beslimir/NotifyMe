package com.example.notifyme.feature_notifications.presentation.notifications

import com.example.notifyme.feature_notifications.domain.util.OrderType

sealed class NotificationEvent {
    data class Order(val orderType: OrderType): NotificationEvent()
    object ToggleOrderSection: NotificationEvent()
}