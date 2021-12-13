package com.example.notifyme.feature_notifications.presentation.notifications.view_model

import android.app.AlarmManager
import android.app.Application
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyme.BaseApplication
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.broadcasts.TimerBroadcast
import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity
import com.example.notifyme.feature_notifications.data.local.PrefsManager
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.use_cases.UseCasesWrapper
import com.example.notifyme.feature_notifications.domain.util.OrderType
import com.example.notifyme.feature_notifications.presentation.TemporaryActivity
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationEvent
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationState
import com.example.notifyme.feature_notifications.util.DataTimeConverter
import com.example.notifyme.feature_notifications.util.DataTimeConverter.getTodayDateMillisFormat
import com.example.notifyme.feature_notifications.util.NotificationUtil
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val useCases: UseCasesWrapper,
    application: Application,
    private val alarmManager: AlarmManager,
    private val prefsManager: PrefsManager
) : AndroidViewModel(application) {

    private val _state = mutableStateOf(NotificationState())
    val state: State<NotificationState> = _state

    private val NOTIFICATION_HOURS = 6
    private val NOTIFICATION_MINUTES = 0
    private val ONE_DAY_IN_MILLIS = 86400000

    private var getNotificationsJob: Job? = null

    init {
        checkStatus()
    }

    private fun checkStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            //if nextItemId == 0, that means the app is never opened before
            if (prefsManager.getNextItemId() == 0) {
                //get all data from local json file
                val myJson =
                    getJsonFromLocalFile(getApplication<Application>().assets.open("notify_me_msg.json"))
                //save retrieved data to Room db
                saveRetrievedDataToRoomDb(myJson)
                //increment nextItemId so it's > 0
                prefsManager.incrementNextItemId()
            }

            withContext(Dispatchers.Main) {
                //show all notifications from Room database in app list
                getAllNotifications(OrderType.Ascending)
            }

            withContext(Dispatchers.Default) {
                //set notification for next item ready
                NotificationUtil(prefsManager, alarmManager, getApplication(), useCases).prepareNotificationForNextItem()
            }
        }
    }

    fun onEvent(event: NotificationEvent) {
        when (event) {
            is NotificationEvent.Order -> {
                if (state.value.orderType::class == event.orderType::class &&
                    state.value.orderType == event.orderType
                ) {
                    return
                }
                getAllNotifications(event.orderType)
            }
            is NotificationEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is NotificationEvent.SendNotification -> {
                NotificationUtil(prefsManager, alarmManager, getApplication(), useCases).sendNotificationNow() //temporary function, for notification testing purpose
            }
            is NotificationEvent.OpenSettings -> {
                Toast.makeText(
                    getApplication(),
                    "Settings implementation in process...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getJsonFromLocalFile(inputStream: InputStream): String {
        var json: String? = null

        var bytes = ByteArray(inputStream.available())
        inputStream.read(bytes, 0, bytes.size)
        json = String(bytes)

        return json
    }

    private suspend fun saveRetrievedDataToRoomDb(myJson: String) {
        val reader = JSONObject(myJson)
        val finalJson: JSONArray = reader.getJSONArray("final_json")
        lateinit var notificationItem: NotificationItem

        val calendar = Calendar.getInstance(TimeZone.getDefault())
        val dateFormat = SimpleDateFormat("dd.MM.yyyy.", Locale.GERMAN)
        val dateString = dateFormat.format(calendar.timeInMillis)
        val todayMillis = DataTimeConverter.convertDateToMillis(dateString)

        for (i in 0 until finalJson.length()) {
            notificationItem = Gson().fromJson(
                finalJson[i].toString(),
                NotificationItem::class.java
            ).also {
                it.date = todayMillis + ONE_DAY_IN_MILLIS * i
                it.color = NotificationItemEntity.notificationItemColors.random().toArgb()
            }
            useCases.insertNotificationUseCase(
                notificationItem
            )
        }

        //save last item id (list size)
        prefsManager.saveLastItemId(finalJson.length())
    }

    private fun getAllNotifications(orderType: OrderType) {
        getNotificationsJob?.cancel()
        getNotificationsJob =
            useCases.getAllNotificationsUseCase(orderType).onEach { notifications ->
                _state.value = state.value.copy(
                    notifications = notifications,
                    orderType = orderType
                )
            }.launchIn(viewModelScope)
    }
}