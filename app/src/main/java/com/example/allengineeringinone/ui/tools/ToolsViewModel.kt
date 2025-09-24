package com.example.allengineeringinone.ui.tools

import android.Manifest
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.example.allengineeringinone.ui.tools.data.model.ToolsUIState
import com.example.allengineeringinone.ui.tools.data.service.AudioRecordingService
import com.example.allengineeringinone.ui.tools.data.service.FlashlightService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ToolsViewModel @Inject constructor(
    private val flashlightService: FlashlightService,
    private val audioRecordingService: AudioRecordingService
) : ViewModel() {
    private val viewModelState = MutableStateFlow(ToolsUIState())
    val uiState = viewModelState.asStateFlow()


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
}