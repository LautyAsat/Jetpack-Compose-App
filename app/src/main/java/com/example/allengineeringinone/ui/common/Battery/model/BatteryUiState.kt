package com.example.allengineeringinone.ui.common.Battery.model

data class BatteryUiState(
    val level: Int = 0,
    val status: BatteryStatus = BatteryStatus.MIDDLE,
    val timeToOffFormatted: String = ""
)
