package com.example.allengineeringinone.ui.map

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.allengineeringinone.ui.home.battery.BatteryRoute
import com.example.allengineeringinone.ui.home.battery.BatteryViewModel

@ExperimentalMaterial3Api
@Composable
fun MapRoute(
    batteryViewModel: BatteryViewModel,
    openDrawer: () -> Unit,
){
    MapScreen(
        openDrawer = openDrawer,
        batteryWidget = { modifier -> BatteryRoute(batteryViewModel, modifier) },
    )
}