package com.example.allengineeringinone.ui.tools

import android.Manifest
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.example.allengineeringinone.ui.tools.data.model.ToolsUIState
import com.example.allengineeringinone.ui.tools.data.service.AudioRecordingService
import com.example.allengineeringinone.ui.tools.data.service.FlashlightService
import com.example.allengineeringinone.ui.tools.data.service.SensorService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.atan2

/**
* @inject flashlightService: Servicio especializado en el manejo del flash
* @inject audioRecordingService: Servicio especializado en el manejo de la grabación de audio
* */
@HiltViewModel
class ToolsViewModel @Inject constructor(
    private val flashlightService: FlashlightService,
    private val audioRecordingService: AudioRecordingService,
    private val sensorService: SensorService
) : ViewModel() {
    private val viewModelState = MutableStateFlow(ToolsUIState())
    val uiState = viewModelState.asStateFlow()

    init {
        // En cuanto se crea el ViewModel, empezamos a escuchar los datos del sensor
        viewModelScope.launch {
            sensorService.accelerometerData.collect { values ->
                // values[0] es gx, values[2] es gz
                val inclinacionActual = calcularInclinacionLateral(values[0], values[2])

                // Actualizamos el estado
                viewModelState.update { it.copy(inclination = inclinacionActual) }
            }
        }
    }


    fun onFlashToggle(){
        if(viewModelState.value.permissionCameraStatus != PermissionStatus.GRANTED) return

        val currentState = uiState.value.isLighterOn

        if (currentState) flashlightService.turnOff()
        else flashlightService.turnOn()

        viewModelState.update { it.copy(isLighterOn = !it.isLighterOn) }
    }

    fun onStartRecording(){
        audioRecordingService.startRecording()
        viewModelState.update { it.copy(isRecording = true) }
    }

    fun onStopRecording(){
        audioRecordingService.stop()
        viewModelState.update { it.copy(isRecording = false) }
    }

    fun onPermissionsResult(permissionsMap: Map<String, Boolean>) {

        val newCameraStatus = if (permissionsMap.containsKey(Manifest.permission.CAMERA)) {
            if (permissionsMap[Manifest.permission.CAMERA] == true) PermissionStatus.GRANTED else PermissionStatus.DENIED
        } else {
            viewModelState.value.permissionCameraStatus
        }

        val newAudioStatus = if (permissionsMap.containsKey(Manifest.permission.RECORD_AUDIO)) {
            if (permissionsMap[Manifest.permission.RECORD_AUDIO] == true) PermissionStatus.GRANTED else PermissionStatus.DENIED
        } else {
            viewModelState.value.permissionAudioStatus
        }


        viewModelState.update { it.copy(
            permissionCameraStatus = newCameraStatus,
            permissionAudioStatus =  newAudioStatus
        )}
    }

    private fun calcularInclinacionLateral(accelX: Float, accelZ: Float): Float {
        // La función atan2 nos da el ángulo en radianes entre el punto (accelZ, accelX) y el eje positivo Z.
        // Esto corresponde directamente al ángulo de inclinación lateral o 'Roll'.
        val anguloEnRadianes = atan2(accelX, accelZ)

        // Convertimos el resultado a grados para que sea más fácil de interpretar y usar en la UI.
        // El rango será aproximadamente de -90° a 90°.
        return (anguloEnRadianes * 180 / Math.PI).toFloat()
    }

}