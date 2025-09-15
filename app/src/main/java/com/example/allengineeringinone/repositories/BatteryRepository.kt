package com.example.allengineeringinone.repositories

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.util.Log
import com.example.allengineeringinone.ui.home.battery.BatteryInfo
import com.example.allengineeringinone.ui.home.battery.BatteryStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BatteryRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    private var lastLevel: Int = -1
    private var lastTimestamp: Long = 0L

    val batteryInfoFlow: Flow<BatteryInfo> = callbackFlow {

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent == null) return

                val status = when (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                    BatteryManager.BATTERY_CAPACITY_LEVEL_LOW -> BatteryStatus.LOW
                    BatteryManager.BATTERY_CAPACITY_LEVEL_NORMAL -> BatteryStatus.MIDDLE
                    BatteryManager.BATTERY_CAPACITY_LEVEL_HIGH -> BatteryStatus.HIGH
                    BatteryManager.BATTERY_CAPACITY_LEVEL_FULL -> BatteryStatus.FULL
                    else -> BatteryStatus.MIDDLE
                }

                val currentLevelBattery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)


                val currentTimestamp = System.currentTimeMillis()
                var estimatedDischargeMillis = -1L

                if ( lastLevel != -1) {
                    val levelDrop = lastLevel - currentLevelBattery
                    val timeDiff = currentTimestamp - lastTimestamp

                    Log.i("BatteryRepository",levelDrop.toString())
                    if (levelDrop > 0 && timeDiff > 0) {
                        val millisPerPercent = timeDiff.toDouble() / levelDrop.toDouble()
                        estimatedDischargeMillis = (millisPerPercent * currentLevelBattery).toLong()
                    }
                }

                lastLevel = currentLevelBattery
                lastTimestamp = currentTimestamp

                trySend(
                    BatteryInfo(
                        level = currentLevelBattery,
                        status = status,
                        estimatedDischargeMillis = estimatedDischargeMillis
                    )
                )
            }
        }

        context.registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        awaitClose { context.unregisterReceiver(receiver) }
    }

}