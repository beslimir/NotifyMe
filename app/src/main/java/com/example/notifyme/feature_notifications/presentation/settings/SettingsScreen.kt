package com.example.notifyme.feature_notifications.presentation.settings

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notifyme.R
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
        stringResource(R.string.settings_item_1),
        stringResource(R.string.settings_item_1_description),
        Icons.Default.Timelapse
    )
    val object2 = SettingsListItemDataClass(
        stringResource(R.string.settings_item_2),
        stringResource(R.string.settings_item_2_description),
        Icons.Default.Assignment
    )
    val object3 = SettingsListItemDataClass(
        stringResource(R.string.settings_item_3),
        stringResource(R.string.settings_item_3_description),
        Icons.Default.Edit
    )
    val object4 = SettingsListItemDataClass(
        stringResource(R.string.settings_item_4),
        stringResource(R.string.settings_item_4_description),
        Icons.Default.Timer
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
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        Toast.makeText(context, context.getString(R.string.settings_icon_1), Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info"
                    )
                }
                IconButton(
                    onClick = {
                        Toast.makeText(context, context.getString(R.string.settings_icon_2), Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "School"
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listOf(object1, object2, object3, object4)) { listItem ->
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