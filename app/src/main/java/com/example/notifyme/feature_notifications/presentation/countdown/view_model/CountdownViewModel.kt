package com.example.notifyme.feature_notifications.presentation.countdown.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notifyme.feature_notifications.presentation.countdown.CountdownDataClass
import com.example.notifyme.feature_notifications.presentation.countdown.CountdownEvent
import com.example.notifyme.feature_notifications.presentation.countdown.CountdownState
import com.example.notifyme.feature_notifications.util.DataTimeConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountdownViewModel @Inject constructor(): ViewModel() {

    private val _state = mutableStateOf(CountdownState())
    val state: State<CountdownState> = _state

    fun onEvent(event: CountdownEvent) {
        when(event) {
            //both events need to be selected in order to make Countdown section visible
            is CountdownEvent.DateSelected -> {
                _state.value = state.value.copy(
                    isDateSelected = true
                )
            }
            is CountdownEvent.TimeSelected -> {
                _state.value = state.value.copy(
                    isTimeSelected = true
                )
            }
        }
    }

    fun calculateRemainingTime(dateTime: String): List<CountdownDataClass> {
        return DataTimeConverter.calculateRemainingTimeFromDateTime(dateTime)
    }

}