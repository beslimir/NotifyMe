package com.example.notifyme.feature_notifications.presentation.statement.view_model

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.notifyme.feature_notifications.presentation.statement.StatementEvent
import com.example.notifyme.feature_notifications.presentation.statement.StatementState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatementViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _state = mutableStateOf(StatementState())
    val state: State<StatementState> = _state

    fun onEvent(event: StatementEvent) {
        when (event) {
            is StatementEvent.OpenStatement -> {
                _state.value = state.value.copy(
                    isStatementOpened = true
                )
            }
        }
    }

}