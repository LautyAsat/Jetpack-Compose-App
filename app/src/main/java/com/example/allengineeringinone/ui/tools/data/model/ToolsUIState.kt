package com.example.allengineeringinone.ui.tools.data.model

import com.example.allengineeringinone.ui.map.data.model.PermissionStatus

data class ToolsUIState(
    val isLighterOn: Boolean = false,
    val permissionCameraStatus: PermissionStatus = PermissionStatus.UNKNOWN,
    val permissionAudioStatus: PermissionStatus = PermissionStatus.UNKNOWN,
    val isRecording: Boolean = false,
)
