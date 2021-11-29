package com.example.notifyme.feature_notifications.presentation.notifications.view_model

import android.app.*
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyme.BaseApplication
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.domain.model.NotificationDetails
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.use_cases.UseCasesWrapper
import com.example.notifyme.feature_notifications.domain.util.OrderType
import com.example.notifyme.feature_notifications.presentation.TemporaryActivity
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationEvent
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationState
import com.example.notifyme.feature_notifications.presentation.notifications.SelectedTime
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.util.*
import javax.inject.Inject


@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val useCases: UseCasesWrapper,
    application: Application,
    private val alarmManager: AlarmManager
) : AndroidViewModel(application), SelectedTime {

    private val _state = mutableStateOf(NotificationState())
    val state: State<NotificationState> = _state

    private var getNotificationsJob: Job? = null

    init {
//        insertTemporaryNotifications()


        //get all data from local json file
        val myJson = getJsonFromLocalFile(application.assets.open("notify_me_msg.json"))

        //save retrieved data to Room db
        saveRetrievedDataToRoomDb(myJson)


//        val reader = JSONObject(myJson)
//        val finalJson: JSONArray = reader.getJSONArray("final_json")
//
//        val list = arrayListOf<NotificationItem>()
//        for (i in 0 until finalJson.length()) {
//            list.add(Gson().fromJson(finalJson[i].toString(), NotificationItem::class.java))
//        }


        //show all notifications from Room database in app list
        getAllNotifications(OrderType.Ascending)
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
                sendNotificationOfFirstItem() //temporary function, for notification testing purpose
            }
            is NotificationEvent.OpenSettings -> {

            }
        }
    }

    private fun insertTemporaryNotifications() {
        val notifDetails: List<NotificationDetails> = listOf(
            NotificationDetails(
                "beslimir",
                "a special person",
                "none",
                "motivational"
            )
        )
        val notifItem = NotificationItem(
            "This is the content",
            notifDetails,
            2,
            "Title"
        )
        viewModelScope.launch {
            useCases.insertNotificationUseCase(notifItem)
        }
    }

    private fun getJsonFromLocalFile(inputStream: InputStream): String {
        var json: String? = null

        var bytes = ByteArray(inputStream.available())
        inputStream.read(bytes, 0, bytes.size)
        json = String(bytes)

        return json
    }

    private fun saveRetrievedDataToRoomDb(myJson: String) {
        val reader = JSONObject(myJson)
        val finalJson: JSONArray = reader.getJSONArray("final_json")

        viewModelScope.launch {
            for (i in 0 until finalJson.length()) {
                useCases.insertNotificationUseCase(
                    Gson().fromJson(
                        finalJson[i].toString(),
                        NotificationItem::class.java
                    )
                )
            }
        }

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

    override fun onSelectedTime(string: String) {
        val hourOfDay = string.substringBefore(":").toInt()
        val minute = string.substringAfter(":").toInt()

        Toast.makeText(getApplication(), "$hourOfDay:$minute", Toast.LENGTH_SHORT).show()
    }

    private fun sendNotificationOfFirstItem() {
        val myIntent = Intent(getApplication(), TemporaryActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            getApplication(),
            0,
            myIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(getApplication(), BaseApplication.MY_CHANNEL)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
            .setContentTitle("Content title")
            .setContentText("Content Text")
            .setPriority(Notification.DEFAULT_SOUND)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(getApplication())
        notificationManager.notify(100, builder.build())
    }

}