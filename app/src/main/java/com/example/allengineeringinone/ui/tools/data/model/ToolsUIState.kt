package com.example.allengineeringinone.ui.tools.data.model

import com.example.allengineeringinone.ui.map.data.model.PermissionStatus

data class ToolsUIState(
    val isLighterOn: Boolean = false,
    val permissionStatus: PermissionStatus = PermissionStatus.UNKNOWN,
    val cameraAction: CameraAction = CameraAction.NONE
)
