package com.example.notifyme.feature_notifications.presentation.notifications.view_model

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyme.BuildConfig
import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity
import com.example.notifyme.feature_notifications.data.local.PrefsManager
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.use_cases.UseCasesWrapper
import com.example.notifyme.feature_notifications.domain.util.OrderType
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationEvent
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationState
import com.example.notifyme.feature_notifications.util.Constants
import com.example.notifyme.feature_notifications.util.Constants.ONE_DAY_IN_MILLIS
import com.example.notifyme.feature_notifications.util.Constants.TAG
import com.example.notifyme.feature_notifications.util.DataTimeConverter
import com.example.notifyme.feature_notifications.util.DataTimeConverter.convertMillisToDate
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
    private val prefsManager: PrefsManager,
    private val notificationUtil: NotificationUtil,
) : AndroidViewModel(application) {

    private val _state = mutableStateOf(NotificationState())
    val state: State<NotificationState> = _state

    private var getNotificationsJob: Job? = null

    init {
        checkStatus()
    }

    private fun checkStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            //if start date == 01.01.1900., that means the app is never opened before
            if (prefsManager.getStartDate() == Constants.START_DATE) {
                //get all data from local json file
                val jsonFile = if (BuildConfig.FLAVOR == "SchatzFlavor") {
                    "notify_me_msg_schatz.json"
                } else {
                    "notify_me_msg_default.json"
                }

                /** Using firebase storage: comment the local file **/
//                val myJson = getJsonFromLocalFile(getApplication<Application>().assets.open(jsonFile))
//                val myJson = readJsonFromFirebase()
                val myJson = useCases.getJsonFromFirebaseUseCase()
                //save retrieved data to Room db and DataStore
                saveRetrievedDataToRoomDb(myJson.toString())

                Log.d(TAG, "checkStatus: First app opening")
            }

            withContext(Dispatchers.Main) {
                //show all notifications from Room database in app list
                getAllNotifications(OrderType.Ascending)
            }

            withContext(Dispatchers.Default) {
                //set notification for next item ready
                notificationUtil.prepareNotificationForNextItem()
            }

            Log.d(TAG, "checkStatus")
        }
    }

    /**
     * Because the data can be updated in the firebase (add new data on top of the old, change the
     * title or content, icon type, etc.), the db must be updated.
     * The data in the Firebase should not be deleted, just modify the mentioned parameters or
     * add new data on top.
     * **/

    fun checkJsonFromFirebase() {
        viewModelScope.launch {
            val newJson = useCases.getJsonFromFirebaseUseCase().toString()
            val lastJson = prefsManager.getJson()
            if (newJson != lastJson) {
                val reader = JSONObject(newJson)
                val lastReader = JSONObject(lastJson)
                val finalJson: JSONArray = reader.getJSONArray("final_json")
                val lastFinalJson: JSONArray = lastReader.getJSONArray("final_json")
                lateinit var notificationItem: NotificationItem
                var counterForNewDate = 1 //for entering new dates of new notification items

                for (i in 0 until finalJson.length()) {
                    notificationItem = Gson().fromJson(
                        finalJson[i].toString(),
                        NotificationItem::class.java
                    ).also {
                        if (lastFinalJson.length() >= i + 1) {
                            //enter old date and color (the user already saw the color, and the date remains the same)
                            val currentNotificationItem = useCases.getNotificationByIdUseCase(i + 1)
                            it.date = currentNotificationItem.date
                            it.color = currentNotificationItem.color
                        } else {
                            //enter new dates (old + 24h) and new colors as the user didn't see the old
                            it.date = useCases.getNotificationByIdUseCase(lastFinalJson.length()).date + ONE_DAY_IN_MILLIS * counterForNewDate
                            it.color = NotificationItemEntity.notificationItemColors.random().toArgb()
                            counterForNewDate++
                        }

                        //save last date to DataStore
                        if (i == finalJson.length() - 1) {
                            val endDate = convertMillisToDate(it.date)
                            prefsManager.saveEndDate(endDate)
                            Log.d(TAG, "saveRetrievedDataToRoomDb: i: $i, date: $endDate")
                        }
                    }

                    useCases.insertNotificationUseCase(
                        notificationItem
                    )
                }

                prefsManager.saveJson(newJson)
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
                notificationUtil.sendNotificationNow() //temporary function, for notification testing purpose
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

    /**
     * This function was used for local files. I will try to use files from the server.
     * **/
    private fun getJsonFromLocalFile(inputStream: InputStream): String {
        var json: String? = ""

        val bytes = ByteArray(inputStream.available())
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

                //save first date to DataStore
                if (i == 0) {
                    val startDate = convertMillisToDate(it.date)
                    prefsManager.saveStartDate(startDate)
                    Log.d(TAG, "saveRetrievedDataToRoomDb: i: $i, date: $startDate")
                }
                //save last date to DataStore
                if (i == finalJson.length() - 1) {
                    val endDate = convertMillisToDate(it.date)
                    prefsManager.saveEndDate(endDate)
                    Log.d(TAG, "saveRetrievedDataToRoomDb: i: $i, date: $endDate")
                }
            }
            useCases.insertNotificationUseCase(
                notificationItem
            )
        }

        prefsManager.saveJson(myJson)
    }

    private fun getAllNotifications(orderType: OrderType) {
        val untilDate = DataTimeConverter.getTodayDateTimeMillisFormat()
        getNotificationsJob?.cancel()
        getNotificationsJob = useCases.getAllNotificationsUntilDateUseCase(orderType, untilDate)
            .onEach { notifications ->
                _state.value = state.value.copy(
                    notifications = notifications,
                    orderType = orderType
                )
            }.launchIn(viewModelScope)
    }
}