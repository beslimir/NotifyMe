package com.example.notifyme.feature_notifications.presentation.settings

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notifyme.feature_notifications.presentation.settings.components.SettingsListItem
import com.example.notifyme.feature_notifications.presentation.settings.components.SettingsListItemDataClass
import com.example.notifyme.feature_notifications.presentation.settings.view_model.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel(),
    context: Context
) {
    //Settings list objects
    val object1 = SettingsListItemDataClass(
        "Time management",
        "Set at what time you want the notifications to appear",
        Icons.Default.Timer
    )
    val object2 = SettingsListItemDataClass(
        "Statement",
        "Click here to accept and read the statement",
        Icons.Default.Assignment
    )
    val object3 = SettingsListItemDataClass(
        "Credits & Info",
        "Read all about the app",
        Icons.Default.Edit
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //TODO: Temporary
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = "Android"
                    )
                }
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "School"
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listOf(object1, object2, object3)) { listItem ->
                SettingsListItem(
                    navController = navController,
                    context = context,
                    listItem = listItem,
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}