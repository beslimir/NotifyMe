package com.example.notifyme.feature_notifications.presentation.countdown

sealed class CountdownObjects(val string: String) {
    object Weeks : CountdownObjects("Weeks")
    object Days : CountdownObjects("Days")
    object Hours : CountdownObjects("Hours")
    object Minutes : CountdownObjects("Minutes")
}