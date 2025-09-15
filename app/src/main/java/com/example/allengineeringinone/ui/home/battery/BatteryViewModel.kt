package com.example.allengineeringinone.ui.home.battery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allengineeringinone.repositories.BatteryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Expone el nivel de batería como un StateFlow.
 * stateIn convierte el "Flow frío" del repositorio en un "Flow caliente",
 * compartiendo el último valor con todos los observadores y manteniéndolo vivo
 * mientras la UI esté visible.
 * */
@HiltViewModel
class BatteryViewModel @Inject constructor(
    // Hilt inyecta el ÚNICO Singleton de BatteryRepository.
    batteryRepository: BatteryRepository
) : ViewModel() {

    val batteryUiState: StateFlow<BatteryUiState> = batteryRepository.batteryInfoFlow
        .map { batteryInfo ->
            BatteryUiState(
                level = batteryInfo.level,
                status = batteryInfo.status,
                timeToOffFormatted = formatTimeRemaining(batteryInfo.estimatedDischargeMillis)
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = BatteryUiState()
        )

    private fun formatTimeRemaining(millis: Long): String {
        if (millis <= 0L) return ""
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60

        return when {
            hours > 0 -> "(${hours}h ${minutes}m)"
            minutes > 0 -> "(${minutes}m)"
            else -> "Cargando..."
        }
    }
}

data class BatteryUiState(
    val level: Int = 0,
    val status: BatteryStatus = BatteryStatus.MIDDLE,
    val timeToOffFormatted: String = "" // String formateado para la UI
)

enum class BatteryStatus {
    LOW,
    MIDDLE,
    HIGH,
    FULL
}

data class BatteryInfo(
    val level: Int = 0,
    val status: BatteryStatus = BatteryStatus.LOW,
    val estimatedDischargeMillis: Long = -1L
)