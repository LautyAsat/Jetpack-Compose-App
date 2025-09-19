package com.example.allengineeringinone.ui.camera
import android.Manifest
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allengineeringinone.ui.camera.data.model.CameraAction
import com.example.allengineeringinone.ui.camera.data.model.CameraUIState
import com.example.allengineeringinone.ui.camera.data.service.CameraService
import com.example.allengineeringinone.ui.camera.data.service.PhotoCaptureService
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
    private val videoRecordingService: VideoRecordingService,
    private val photoCaptureService: PhotoCaptureService,
    private val cameraService: CameraService,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(CameraUIState())
    val uiState = viewModelState.asStateFlow()

    //casos de uso
    private val previewUseCase = Preview.Builder().build()
    private val imageCaptureUseCase = ImageCapture.Builder().build()
    private val videoCaptureUseCase = VideoCapture.withOutput(Recorder.Builder().build())

    fun initializeCamera(lifecycleOwner: LifecycleOwner, previewView: PreviewView) {
        previewUseCase.setSurfaceProvider(previewView.surfaceProvider)
        bindCamera(lifecycleOwner)
    }

    private fun bindCamera(lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            // Según el modo actual, decidimos qué casos de uso vincular
            val imageCapture = if (viewModelState.value.cameraAction == CameraAction.PHOTO) imageCaptureUseCase else null
            val videoCapture = if (viewModelState.value.cameraAction == CameraAction.VIDEO) videoCaptureUseCase else null

            cameraService.bindUseCases(
                lifecycleOwner,
                viewModelState.value.selectedCamera,
                previewUseCase,
                imageCapture,
                videoCapture
            )

            viewModelState.update { it.copy(isCameraReady = true) }
        }
    }

    fun onTakePhotoClick() {
        if (!viewModelState.value.isCameraReady) return
        photoCaptureService.takePhoto(imageCaptureUseCase)
    }

    fun onStartRecording(){
        if(viewModelState.value.permissionCameraStatus != PermissionStatus.GRANTED || !viewModelState.value.isCameraReady || viewModelState.value.cameraAction != CameraAction.VIDEO) {
            Log.d("DEBUG_VIDEO", "No llego ni a pasar el viewmodel") // <-- LOG 9
            return
        }

        videoRecordingService.startRecording(videoCaptureUseCase)
        viewModelState.update { it.copy(isRecording = true)}
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
    }

    fun onPhotoMode(){
        viewModelState.update { it.copy(cameraAction = CameraAction.PHOTO) }
    }

    fun onVideoMode(){
        viewModelState.update { it.copy(cameraAction = CameraAction.VIDEO) }
    }

    fun onSwitchCamera() {
        val currentSelector = viewModelState.value.selectedCamera
        val newSelector = if (currentSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }

        viewModelState.update { it.copy(selectedCamera = newSelector, isCameraReady = false) }
    }
}





