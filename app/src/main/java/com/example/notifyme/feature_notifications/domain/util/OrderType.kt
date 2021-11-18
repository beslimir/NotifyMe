package com.example.notifyme.feature_notifications.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}