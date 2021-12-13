package com.example.notifyme.feature_notifications.presentation.credits.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.notifyme.feature_notifications.data.local.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CreditsViewModel @Inject constructor(
    application: Application,
    private val prefsManager: PrefsManager
) : AndroidViewModel(application) {

    fun getCreditsText(): String {
        var date: String
        runBlocking {
            date = prefsManager.getStartDate() + "-" + prefsManager.getEndDate()
        }

        return date
    }
}