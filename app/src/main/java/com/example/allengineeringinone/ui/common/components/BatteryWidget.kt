package com.example.allengineeringinone.ui.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.R
import com.example.allengineeringinone.ui.common.Battery.BatteryViewModel

@Composable
fun BatteryWidget(
    modifier: Modifier = Modifier
) {
    val viewModel: BatteryViewModel = hiltViewModel()

    val batteryLevel by viewModel.batteryUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.padding(4.dp, 4.dp, 16.dp, 4.dp )
    ){
        Image(
            painter = painterResource(R.drawable.ic_battery_f4),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )

        Row {
            Text("${batteryLevel.level}%")
            Spacer(Modifier.width(4.dp))
            Text(batteryLevel.timeToOffFormatted)
        }
    }
}