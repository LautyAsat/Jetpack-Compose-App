package com.example.allengineeringinone.ui.tools

import androidx.compose.runtime.Composable
import com.example.allengineeringinone.ui.home.battery.BatteryRoute
import com.example.allengineeringinone.ui.home.battery.BatteryViewModel

@Composable
fun ToolsRoute(
    batteryViewModel: BatteryViewModel,
    openDrawer: () -> Unit
){
    ToolsScreen(openDrawer, batteryWidget = { modifier -> BatteryRoute(batteryViewModel, modifier) })
}