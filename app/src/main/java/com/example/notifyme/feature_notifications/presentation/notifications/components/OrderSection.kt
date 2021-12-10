package com.example.notifyme.feature_notifications.presentation.notifications.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.notifyme.R
import com.example.notifyme.feature_notifications.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    orderType: OrderType = OrderType.Ascending,
    onOrderChange: (OrderType) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                selected = orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(OrderType.Ascending)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.descending),
                selected = orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(OrderType.Descending)
                }
            )
        }
    }
}