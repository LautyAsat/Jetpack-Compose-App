package com.example.allengineeringinone.ui.home.battery

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.AndroidViewModel
import com.example.allengineeringinone.repositories.BatteryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BatteryViewModel(
    application: Application
) : AndroidViewModel(application){

    private val batteryRepository: BatteryRepository = BatteryRepository()

    private val viewModelState = MutableStateFlow(
        BatteryViewModelState(
            BatteryCapacityState.MEDIUM,
            60
        ),
    )

    val uiState: StateFlow<BatteryViewModelState> = viewModelState.asStateFlow()


    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val batteryPercentage = batteryRepository.getBatteryLevel(getApplication())

            // Actualizamos el StateFlow
            viewModelState.update {
                it.copy(
                    batteryLevel = BatteryCapacityState.MEDIUM,
                    batteryLevelPercentage = batteryPercentage
                )
            }
        }
    }

    init {
        // Registramos el receiver usando applicationContext
        getApplication<Application>().registerReceiver(
            batteryReceiver,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )

    }
}

data class BatteryViewModelState(
    val batteryLevel: BatteryCapacityState,
    val batteryLevelPercentage: Int = 0
)

enum class BatteryCapacityState{
    LOW, MEDIUM, HIGH, FULL
}
