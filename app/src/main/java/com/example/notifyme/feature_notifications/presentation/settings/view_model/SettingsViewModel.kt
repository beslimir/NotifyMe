package com.example.notifyme.feature_notifications.presentation.settings.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyme.feature_notifications.data.local.PrefsManager
import com.example.notifyme.feature_notifications.presentation.settings.SettingsEvent
import com.example.notifyme.feature_notifications.util.DataTimeConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefsManager: PrefsManager,
    application: Application
) : AndroidViewModel(application) {

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SaveNotificationTime -> {
                viewModelScope.launch(Dispatchers.IO) {
                    prefsManager.saveNotificationTime(DataTimeConverter.convertTimeToMillis(event.time))
                }
            }
            is SettingsEvent.GetNotificationTime -> {
                viewModelScope.launch(Dispatchers.IO) {
                    Log.d("aaaa", "onEvent: Time: ${prefsManager.getNotificationTime()}")
                }
            }
        }
    }

}