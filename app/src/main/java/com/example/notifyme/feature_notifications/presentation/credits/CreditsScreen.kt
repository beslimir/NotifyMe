package com.example.notifyme.feature_notifications.presentation.credits

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.presentation.credits.view_model.CreditsViewModel

@Composable
fun CreditsScreen(
    viewModel: CreditsViewModel = hiltViewModel()
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
                text = stringResource(R.string.settings_item_3),
                style = MaterialTheme.typography.h6
            )
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = stringResource(R.string.settings_item_3),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val dates = viewModel.getCreditsText()
            Text(
                text = stringResource(
                    R.string.credits_text,
                    dates.substringBefore("-"),
                    dates.substringAfter("-")
                ),
                textAlign = TextAlign.Justify
            )
        }

    }
}