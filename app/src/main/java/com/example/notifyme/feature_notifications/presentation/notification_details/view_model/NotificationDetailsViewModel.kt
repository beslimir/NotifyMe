package com.example.notifyme.feature_notifications.presentation.notification_details.view_model

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity
import com.example.notifyme.feature_notifications.domain.model.NotificationDetails
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.use_cases.UseCasesWrapper
import com.example.notifyme.feature_notifications.presentation.notification_details.NotificationDetailsEvent
import com.example.notifyme.feature_notifications.util.DataTimeConverter.convertDateToMillis
import com.example.notifyme.feature_notifications.util.DataTimeConverter.getTodayDateStringFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationDetailsViewModel @Inject constructor(
    private val useCases: UseCasesWrapper,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    private val _notificationItemContent = mutableStateOf("Notification Item Content")
    val notificationItemContent: State<String> = _notificationItemContent

    private val notificationDetails = NotificationDetails(
        author = "beslimir",
        dedicated_to = "a special person",
        icon_type = "fire",
        type = "motivational"
    )
    private val _notificationItemDetails = mutableStateOf(notificationDetails)
    val notificationItemDetails: State<NotificationDetails> = _notificationItemDetails

    private val _notificationItemTitle = mutableStateOf("Notification Item Title")
    val notificationItemTitle: State<String> = _notificationItemTitle

    private val _notificationItemDate = mutableStateOf(convertDateToMillis(getTodayDateStringFormat()))
    val notificationItemDate: State<Long> = _notificationItemDate

    private val _notificationItemColor = mutableStateOf(NotificationItemEntity.notificationItemColors.random().toArgb())
    val notificationItemColor: State<Int> = _notificationItemColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNotificationId: Int = -1

    init {
        savedStateHandle.get<Int>("notificationId")?.let { notificationId ->
            if (notificationId != -1) {
                viewModelScope.launch {
                    useCases.getNotificationByIdUseCase(notificationId).also { notificationItem ->
                        currentNotificationId = notificationItem.id
                        _notificationItemTitle.value = notificationItem.title
                        _notificationItemContent.value = notificationItem.content
                        _notificationItemDetails.value = notificationItem.details[0]
                        _notificationItemDate.value = notificationItem.date
                        _notificationItemColor.value = notificationItem.color
                    }
                }
            }
        }
    }

    fun onEvent(event: NotificationDetailsEvent) {
        when (event) {
            is NotificationDetailsEvent.ChangeColor -> {
                _notificationItemColor.value = event.color
            }
            is NotificationDetailsEvent.SaveNotificationItem -> {
                viewModelScope.launch {
                    try {
                        useCases.insertNotificationUseCase(
                            NotificationItem(
                                content = notificationItemContent.value,
                                details = listOf(notificationItemDetails.value),
                                id = currentNotificationId,
                                title = notificationItemTitle.value,
                                date = notificationItemDate.value,
                                color = notificationItemColor.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNotificationItem)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: getApplication<Application>().getString(R.string.notification_not_saved)
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNotificationItem : UiEvent()
    }

}