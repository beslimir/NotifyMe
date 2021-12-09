package com.example.notifyme.feature_notifications.presentation.statement

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.presentation.settings.view_model.SettingsViewModel

@Composable
fun StatementScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    context: Context
) {

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
                text = "Statement",
                style = MaterialTheme.typography.h6
            )

            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.Mail,
                    contentDescription = "Mail"
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            var text by remember { mutableStateOf("With this statement, I confess that...") }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Text(text = text)
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //TODO: Temporary...
                Image(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "Click to reveal the full text",
                    modifier = Modifier
                        .size(144.dp)
                        .clickable {
                            text = "With this statement, I confess that FC Bayern beat Barcelona last night 3:0!"
                        }
                )
            }
        }
    }
}