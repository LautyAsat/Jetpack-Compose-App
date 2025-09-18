package com.example.allengineeringinone.ui.camera
import android.Manifest
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allengineeringinone.ui.camera.data.model.CameraAction
import com.example.allengineeringinone.ui.camera.data.model.CameraUIState
import com.example.allengineeringinone.ui.camera.data.service.VideoRecordingService
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val videoRecordingService: VideoRecordingService
) : ViewModel() {

    private val viewModelState = MutableStateFlow(CameraUIState())
    val uiState = viewModelState.asStateFlow()

    fun initializeCamera(lifecycleOwner: LifecycleOwner, previewView: PreviewView) {
        viewModelScope.launch {
            // Le pasamos el 'surfaceProvider' de la PreviewView al servicio
            videoRecordingService.initialize(lifecycleOwner, previewView.surfaceProvider)
            viewModelState.update { it.copy(isCameraReady = true) }
        }
    }

    fun onStartRecording(){
        if(viewModelState.value.permissionCameraStatus != PermissionStatus.GRANTED || !viewModelState.value.isCameraReady) {
            Log.d("DEBUG_VIDEO", "No llego ni a pasar el viewmodel") // <-- LOG 9
            viewModelState.update { it.copy(cameraAction = CameraAction.VIDEO) }
            return
        }

        videoRecordingService.startRecording()
        viewModelState.update { it.copy(isRecording = true) }
    }

    fun onStopRecording() {
        videoRecordingService.stopRecording()
        viewModelState.update { it.copy(isRecording = false) }
    }

    fun onScreenDisposed() {
        videoRecordingService.releaseCamera()
        viewModelState.update { it.copy(isCameraReady = false) }
    }


    fun onPermissionsResult(permissionsMap: Map<String, Boolean>) {

        Log.i("DEBUG_VIDEO", " $permissionsMap")

        val isCameraGranted = permissionsMap[Manifest.permission.CAMERA]
            ?: (viewModelState.value.permissionCameraStatus == PermissionStatus.GRANTED)

        val isAudioGranted = permissionsMap[Manifest.permission.RECORD_AUDIO]
            ?: (viewModelState.value.permissionAudioStatus == PermissionStatus.GRANTED)

        Log.i("DEBUG_VIDEO", " $isCameraGranted $isAudioGranted")

        viewModelState.update { it.copy(
            permissionCameraStatus = if (isCameraGranted) PermissionStatus.GRANTED else PermissionStatus.DENIED,
            permissionAudioStatus = if (isAudioGranted) PermissionStatus.GRANTED else PermissionStatus.DENIED
        )}

        // Si se concedió el permiso que necesitábamos, ejecutamos la acción pendiente
        if (isCameraGranted) {
            when (viewModelState.value.cameraAction) {
                CameraAction.VIDEO -> {
                    // Si también se concedió el de audio, el usuario puede volver a intentar
                    // la acción de grabar, que ahora estará habilitada.
                }
                else -> {}
            }
        }

        // Limpiamos la acción pendiente
        viewModelState.update { it.copy(cameraAction = CameraAction.NONE) }
    }
}





