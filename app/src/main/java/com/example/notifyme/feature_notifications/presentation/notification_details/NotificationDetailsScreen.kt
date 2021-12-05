package com.example.notifyme.feature_notifications.presentation.notification_details

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity
import com.example.notifyme.feature_notifications.presentation.notification_details.components.TransparentTextField
import com.example.notifyme.feature_notifications.presentation.notification_details.view_model.NotificationDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NotificationDetailsScreen(
    navController: NavController,
    notificationColor: Int,
    viewModel: NotificationDetailsViewModel = hiltViewModel()
) {
    val titleState = viewModel.notificationItemTitle.value
    val contentState = viewModel.notificationItemContent.value
    val dateState = viewModel.notificationItemDate.value
    val detailsState = viewModel.notificationItemDetails.value
    val scaffoldState = rememberScaffoldState()
    val notificationBackgroundAnimatable = remember {
        Animatable(
            Color(
                if (notificationColor != -1) {
                    notificationColor
                } else {
                    viewModel.notificationItemColor.value
                }
            )
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is NotificationDetailsViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is NotificationDetailsViewModel.UiEvent.SaveNotificationItem -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(NotificationDetailsEvent.SaveNotificationItem)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save notification"
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(notificationBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NotificationItemEntity.notificationItemColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = 3.dp,
                            color = if (viewModel.notificationItemColor.value == colorInt) {
                                Color.Black
                            } else {
                                Color.Transparent
                            },
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                notificationBackgroundAnimatable.animateTo(
                                    targetValue = Color(colorInt),
                                    animationSpec = tween(
                                        durationMillis = 500
                                    )
                                )
                            }
                            viewModel.onEvent(NotificationDetailsEvent.ChangeColor(colorInt))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                text = titleState,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                text = contentState,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}