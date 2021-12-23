package com.example.notifyme.feature_notifications.presentation.credits

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.presentation.credits.view_model.CreditsViewModel
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri

@Composable
fun CreditsScreen(
    context: Context,
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
            Spacer(modifier = Modifier.height(16.dp))

            //TODO: Can be extracted to a separate component
            //Create the annotation for used icons
            val annotatedString = buildAnnotatedString {
                append(stringResource(R.string.credits_icons_1))

                pushStringAnnotation(tag = "creator", annotation = stringResource(R.string.credits_icons_creator_link))
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append(" " + stringResource(R.string.credits_icons_creator))
                }

                append(stringResource(R.string.credits_icons_2))

                pushStringAnnotation(tag = "page", annotation = stringResource(R.string.credits_icons_page_link))
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append(" " + stringResource(R.string.credits_icons_page))
                }

                pop()
            }

            ClickableText(text = annotatedString, style = TextStyle(color = Color.White), onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "creator", start = offset, end = offset).firstOrNull()?.let {
                    val uri: Uri = Uri.parse(context.getString(R.string.credits_icons_creator_link))
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(context, intent, null)
                }
                annotatedString.getStringAnnotations(tag = "page", start = offset, end = offset).firstOrNull()?.let {
                    val uri: Uri = Uri.parse(context.getString(R.string.credits_icons_page_link))
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(context, intent, null)
                }
            })
        }

    }
}