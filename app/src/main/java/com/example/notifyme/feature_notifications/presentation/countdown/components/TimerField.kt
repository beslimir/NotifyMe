package com.example.notifyme.feature_notifications.presentation.countdown.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notifyme.feature_notifications.presentation.countdown.TimerDataClass

@Composable
fun TimerField(
    timerDataClass: TimerDataClass
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .shadow(2.dp, CircleShape)
                .clip(CircleShape)
                .border(
                    width = 3.dp,
                    color = Color.White,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = timerDataClass.num.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = timerDataClass.objectType.string)
    }
}