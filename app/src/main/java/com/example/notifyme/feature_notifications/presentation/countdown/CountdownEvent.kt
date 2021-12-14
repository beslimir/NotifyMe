package com.example.notifyme.feature_notifications.presentation.countdown

sealed class CountdownEvent {
    object DateSelected : CountdownEvent()
    object TimeSelected : CountdownEvent()
}