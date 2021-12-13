package com.example.notifyme.feature_notifications.presentation.statement.view_model

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifyme.feature_notifications.data.local.PrefsManager
import com.example.notifyme.feature_notifications.presentation.statement.StatementEvent
import com.example.notifyme.feature_notifications.presentation.statement.StatementState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class StatementViewModel @Inject constructor(
    application: Application,
    private val prefsManager: PrefsManager
) : AndroidViewModel(application) {

    private val _state = mutableStateOf(StatementState())
    val state: State<StatementState> = _state

    fun onEvent(event: StatementEvent) {
        when (event) {
            is StatementEvent.OpenStatement -> {
                _state.value = state.value.copy(
                    isStatementOpened = true
                )
                acceptStatement()
            }
        }
    }

    fun isStatementAccepted(): Boolean {
        var isAccepted: Boolean
        runBlocking {
            isAccepted = prefsManager.getStatement()
        }

        return isAccepted
    }

    fun acceptStatement() {
        viewModelScope.launch {
            prefsManager.saveStatement()
        }
    }


}