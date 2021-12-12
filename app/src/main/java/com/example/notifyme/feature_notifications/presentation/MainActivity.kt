package com.example.notifyme.feature_notifications.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notifyme.feature_notifications.presentation.countdown.CountdownScreen
import com.example.notifyme.feature_notifications.presentation.credits.CreditsScreen
import com.example.notifyme.feature_notifications.presentation.ui.theme.NotifyMeAppTheme
import com.example.notifyme.feature_notifications.presentation.notification_details.NotificationDetailsScreen
import com.example.notifyme.feature_notifications.presentation.notifications.NotificationsScreen
import com.example.notifyme.feature_notifications.presentation.settings.SettingsScreen
import com.example.notifyme.feature_notifications.presentation.statement.StatementScreen

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotifyMeAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotificationsScreen.route
                    ) {
                        composable(route = Screen.NotificationsScreen.route) {
                            NotificationsScreen(navController = navController, this@MainActivity)
                        }
                        composable(route = Screen.NotificationDetailsScreen.route +
                                "?notificationId={notificationId}&notificationColor={notificationColor}",
                            arguments = listOf(
                                navArgument(name = "notificationId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "notificationColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("notificationColor") ?: -1
                            NotificationDetailsScreen(
                                navController = navController,
                                notificationColor = color
                            )
                        }
                        composable(route = Screen.SettingsScreen.route) {
                            SettingsScreen(navController = navController, context = this@MainActivity)
                        }
                        composable(route = Screen.StatementScreen.route) {
                            StatementScreen(context = this@MainActivity)
                        }
                        composable(route = Screen.CreditsScreen.route) {
                            CreditsScreen()
                        }
                        composable(route = Screen.CountdownScreen.route) {
                            CountdownScreen()
                        }
                    }
                }
            }
        }
    }
}