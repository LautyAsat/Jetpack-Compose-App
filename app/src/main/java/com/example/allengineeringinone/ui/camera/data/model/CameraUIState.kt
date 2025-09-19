package com.example.allengineeringinone.ui.camera.data.model

import androidx.camera.core.CameraSelector
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus

data class CameraUIState(
    val isRecording: Boolean = false,
    val permissionCameraStatus: PermissionStatus = PermissionStatus.UNKNOWN,
    val permissionAudioStatus: PermissionStatus = PermissionStatus.UNKNOWN,
    val cameraAction: CameraAction = CameraAction.PHOTO,
    val isCameraReady: Boolean = false,
    val selectedCamera: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
)
