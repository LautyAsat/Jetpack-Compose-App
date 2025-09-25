package com.example.allengineeringinone.ui.tools.data.service

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


@Singleton // Hacemos que sea un Singleton para que solo haya una instancia
class SensorService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // Este Flow emitirá los valores del acelerómetro cada vez que cambien
    val accelerometerData: Flow<FloatArray> = callbackFlow {
        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                    // trySend es la forma de emitir un valor desde un callbackFlow
                    trySend(event.values.clone()) // Enviamos una copia para seguridad
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // esto es para toquetear temas de presicion, no creo que lo usemos porque es complejo
            }
        }

        // Registramos el listener cuando el Flow es recolectado
        sensorManager.registerListener(
            sensorListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )

        // esto hace que el proceso se detenga cuando la app anda en segundo plano
        // para que la app no drene bateria
        awaitClose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}