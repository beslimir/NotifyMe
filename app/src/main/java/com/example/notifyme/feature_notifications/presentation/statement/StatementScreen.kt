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
import androidx.compose.ui.res.stringResource
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
                text = stringResource(R.string.settings_item_2),
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
            var text by remember { mutableStateOf(context.getString(R.string.statement_text_1)) }
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
                        contentDescription = stringResource(R.string.statement_text_click_reveal),
                        modifier = Modifier
                            .size(144.dp)
                            .clickable {
                                viewModel.onEvent(StatementEvent.OpenStatement)
                                text =
                                    context.getString(R.string.statement_text_2)
                            }
                    )
                    Text(
                        text = if (!state.isStatementOpened) {
                            stringResource(R.string.statement_text_click_accept)
                        } else {
                            stringResource(R.string.statement_text_accepted)
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
                            contentDescription = stringResource(R.string.statement_text_full_revealed),
                            modifier = Modifier
                                .size(144.dp)
                        )
                        Text(text = stringResource(R.string.statement_text_accepted))
                    }
                }

            }

        }
    }
}