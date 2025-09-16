package com.example.allengineeringinone.ui.tools

import androidx.lifecycle.ViewModel
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.example.allengineeringinone.ui.tools.data.model.CameraAction
import com.example.allengineeringinone.ui.tools.data.model.ToolsUIState
import com.example.allengineeringinone.ui.tools.data.service.FlashlightService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/*
* Linterna
* Regla
*
* */

@HiltViewModel
class ToolsViewModel @Inject constructor(
    private val flashlightService: FlashlightService
) : ViewModel() {
    private val viewModelState = MutableStateFlow(ToolsUIState())
    val uiState = viewModelState.asStateFlow()


    fun onFlashToggle(){
        if(viewModelState.value.permissionStatus != PermissionStatus.GRANTED) return;

        val currentState = uiState.value.isLighterOn

        if (currentState) {
            flashlightService.turnOff()
        } else {
            flashlightService.turnOn()
        }

        viewModelState.update { it.copy(isLighterOn = !it.isLighterOn, cameraAction = CameraAction.FLASH) }
    }

    fun onPermissionResult(isGranted: Boolean){
        val newStatus = if (isGranted) PermissionStatus.GRANTED else PermissionStatus.DENIED

        viewModelState.value = viewModelState.value.copy(permissionStatus = newStatus)
    }

    fun onCameraAction(newCameraAction: CameraAction){
        viewModelState.value = viewModelState.value.copy(cameraAction = newCameraAction)
    }
}