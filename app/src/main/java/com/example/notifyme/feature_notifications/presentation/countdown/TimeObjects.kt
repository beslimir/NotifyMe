package com.example.notifyme.feature_notifications.presentation.countdown

sealed class TimeObjects(val string: String) {
    object Weeks : TimeObjects("Weeks")
    object Days : TimeObjects("Days")
    object Hours : TimeObjects("Hours")
    object Minutes : TimeObjects("Minutes")
}