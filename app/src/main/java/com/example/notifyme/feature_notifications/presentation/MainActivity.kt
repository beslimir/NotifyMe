package com.example.notifyme.feature_notifications.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationsScreen
import com.example.notifyme.feature_notifications.presentation.ui.theme.NotifyMeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotifyMeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotificationsScreen.route
                    ) {
                        composable(route = Screen.NotificationsScreen.route) {
                            NotificationsScreen(navController = navController, this@MainActivity)
                        }
                    }
                }
            }
        }
    }
}