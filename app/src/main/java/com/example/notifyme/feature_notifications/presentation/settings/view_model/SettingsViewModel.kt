package com.example.notifyme.feature_notifications.presentation.settings.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyme.feature_notifications.data.local.PrefsManager
import com.example.notifyme.feature_notifications.domain.use_cases.UseCasesWrapper
import com.example.notifyme.feature_notifications.presentation.settings.SettingsEvent
import com.example.notifyme.feature_notifications.util.DataTimeConverter
import com.example.notifyme.feature_notifications.util.NotificationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCases: UseCasesWrapper,
    private val prefsManager: PrefsManager,
    application: Application,
    private val notificationUtil: NotificationUtil
) : AndroidViewModel(application) {

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SaveNotificationTime -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val currentNotificationTime = prefsManager.getNotificationTime()
                    prefsManager.saveNotificationTime(DataTimeConverter.convertTimeToMillis(event.time))
                    val newNotificationTime = prefsManager.getNotificationTime()

                    val difference = newNotificationTime - currentNotificationTime
                    useCases.updateDateToAllNotificationsUseCase(difference)

                    notificationUtil.prepareNotificationForNextItem()
                }
            }
            is SettingsEvent.GetNotificationTime -> {
                viewModelScope.launch(Dispatchers.IO) {
                    Log.d("aaaa", "onEvent: Time: ${prefsManager.getNotificationTime()}")
                }
            }
        }
    }

    fun getNotificationTimeAsString(): String = runBlocking {
        DataTimeConverter.convertMillisToTime(prefsManager.getNotificationTime())
    }


}