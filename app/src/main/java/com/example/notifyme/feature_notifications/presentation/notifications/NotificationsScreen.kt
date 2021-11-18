package com.example.notifyme.feature_notifications.presentation.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notifyme.feature_notifications.presentation.Screen
import com.example.notifyme.feature_notifications.presentation.notifications.components.NotificationListItem
import com.example.notifyme.feature_notifications.presentation.notifications.view_model.NotificationsViewModel

@Composable
fun NotificationsScreen(
    navController: NavController,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.notifications) { notification ->
                NotificationListItem(
                    notificationItem = notification,
                    onItemClick = {
                        navController.navigate(Screen.NotificationDetailsScreen.route + "/${notification.id}")
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }


}