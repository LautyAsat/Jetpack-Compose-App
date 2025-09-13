package com.example.allengineeringinone.ui.home.battery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.allengineeringinone.R


@Composable
fun BatteryRoute(
    batteryViewModel: BatteryViewModel,
    modifier: Modifier
){
    val uiState: BatteryViewModelState by batteryViewModel.uiState.collectAsStateWithLifecycle()

    BatteryWidget(uiState.batteryLevelPercentage)
}

@Composable
fun BatteryWidget(batteryLevel: Int){
    Column(
        modifier = Modifier.padding(4.dp, 4.dp, 16.dp, 4.dp )
    ){
        Image(
            painter = painterResource(R.drawable.ic_battery_f4), // o el ícono que quieras
            contentDescription = null,                // null si es puramente decorativo
            modifier = Modifier.size(28.dp)
        )
        Text("$batteryLevel%")
    }
}