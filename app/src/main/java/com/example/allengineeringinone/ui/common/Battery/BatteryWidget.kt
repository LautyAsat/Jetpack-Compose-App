package com.example.allengineeringinone.ui.common.Battery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allengineeringinone.R

@Composable
fun BatteryWidget(
    modifier: Modifier = Modifier
) {
    // 1. El Widget pide su propio ViewModel a Hilt.
    // Hilt gestiona su ciclo de vida de forma inteligente.
    val viewModel: BatteryViewModel = hiltViewModel()

    // 2. Observa el estado del ViewModel.
    val batteryLevel by viewModel.batteryUiState.collectAsStateWithLifecycle()

    // 3. Dibuja la UI con el estado actual.
    Column(
        modifier = Modifier.padding(4.dp, 4.dp, 16.dp, 4.dp )
    ){
        Image(
            painter = painterResource(R.drawable.ic_battery_f4), // o el ícono que quieras
            contentDescription = null,                // null si es puramente decorativo
            modifier = Modifier.size(28.dp)
        )

        Row {
            Text("${batteryLevel.level}%")
            Spacer(Modifier.width(4.dp))
            Text("${batteryLevel.timeToOffFormatted}")
        }
    }
}