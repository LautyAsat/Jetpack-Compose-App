package com.example.allengineeringinone.ui.tools

import android.Manifest
import androidx.lifecycle.ViewModel
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.example.allengineeringinone.ui.tools.data.model.ToolsUIState
import com.example.allengineeringinone.ui.tools.data.service.FlashlightService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ToolsViewModel @Inject constructor(
    private val flashlightService: FlashlightService,
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

    fun onPermissionsResult(permissionsMap: Map<String, Boolean>) {

        val isCameraGranted = permissionsMap[Manifest.permission.CAMERA]
            ?: (viewModelState.value.permissionCameraStatus == PermissionStatus.GRANTED)

        viewModelState.update { it.copy(
            permissionCameraStatus = if (isCameraGranted) PermissionStatus.GRANTED else PermissionStatus.DENIED,
        )}
    }
}