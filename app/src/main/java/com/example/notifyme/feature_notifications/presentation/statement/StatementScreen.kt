package com.example.notifyme.feature_notifications.presentation.statement

import android.content.Context
import androidx.compose.animation.*
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
import com.example.notifyme.feature_notifications.presentation.statement.view_model.StatementViewModel

@ExperimentalAnimationApi
@Composable
fun StatementScreen(
    viewModel: StatementViewModel = hiltViewModel(),
    context: Context
) {
    val state = viewModel.state.value

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

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            var text by remember { mutableStateOf("With this statement, I confess that...") }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = text)
            }
            //TODO: Temporary...
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(6f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_heart_shape),
                        contentDescription = "Click to reveal the full text",
                        modifier = Modifier
                            .size(144.dp)
                            .clickable {
                                viewModel.onEvent(StatementEvent.OpenStatement)
                                text =
                                    "With this statement, I confess that FC Bayern beat Barcelona last night 3:0!"
                            }
                    )
                    Text(
                        text = if (!state.isStatementOpened) {
                            "Click me to accept and read the statement!"
                        } else {
                            "Statement accepted!"
                        }
                    )
                }

                this@Column.AnimatedVisibility(
                    visible = state.isStatementOpened,
                    enter = fadeIn()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = "Full text revealed",
                            modifier = Modifier
                                .size(144.dp)
                        )
                        Text(text = "Statement accepted!")
                    }
                }

            }

        }
    }
}