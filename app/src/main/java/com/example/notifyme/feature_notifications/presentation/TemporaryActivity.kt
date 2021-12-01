package com.example.notifyme.feature_notifications.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.data.local.PrefsManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TemporaryActivity : AppCompatActivity() {

//    private lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temporary_layout)

//        prefsManager = PrefsManager(this)
//
//        prefsManager.getOpenedFlag.asLiveData().observe(this, { item ->
//            Log.d("aaaa", "111111: $item")
//        })
//
//        GlobalScope.launch {
//            prefsManager.setOpenedFlag()
//            Log.d("aaaa", "intermezzo")
//        }
//
//        prefsManager.getOpenedFlag.asLiveData().observe(this, { item ->
//            Log.d("aaaa", "222222: $item")
//        })


    }
}