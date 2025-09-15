package com.example.allengineeringinone.ui.tools

import androidx.compose.runtime.Composable
import com.example.allengineeringinone.ui.common.Battery.BatteryWidget

@Composable
fun ToolsRoute(
    openDrawer: () -> Unit
){
    ToolsScreen(
        openDrawer,
        batteryWidget = { modifier -> BatteryWidget(modifier) },
    )
}