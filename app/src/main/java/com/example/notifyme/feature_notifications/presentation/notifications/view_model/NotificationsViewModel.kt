package com.example.notifyme.feature_notifications.presentation.notifications.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyme.feature_notifications.domain.model.NotificationDetails
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.use_cases.UseCasesWrapper
import com.example.notifyme.feature_notifications.domain.util.OrderType
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationEvent
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject
import android.app.Application
import com.google.gson.Gson
import org.json.JSONObject

import org.json.JSONArray


@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val useCases: UseCasesWrapper,
    application: Application
) : AndroidViewModel(application) {

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

}