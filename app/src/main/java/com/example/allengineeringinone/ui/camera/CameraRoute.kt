package com.example.allengineeringinone.ui.camera

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.camera.data.model.CameraAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraRoute(
    cameraViewModel: CameraViewModel = hiltViewModel()
){

    val uiState by cameraViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    // --- LAUNCHER PARA CÁMARA Y AUDIO ---
    val videoPermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsMap ->
            cameraViewModel.onPermissionsResult(permissionsMap)
        }
    )


    /*
    * Verificamos si ya tenemos los permisos
    * */
    DisposableEffect(uiState.selectedCamera) {
        val isCameraGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val isAudioGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED


        if(!isCameraGranted && !isAudioGranted){
            videoPermissionsLauncher.launch(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            )
        }

        // 2. Informamos al ViewModel con el mapa corregido
        cameraViewModel.onPermissionsResult(mapOf(
            Manifest.permission.CAMERA to isCameraGranted,
            Manifest.permission.RECORD_AUDIO to isAudioGranted // <-- BUG CORREGIDO
        ))

        // 3. Inicializamos la cámara
        cameraViewModel.initializeCamera(lifecycleOwner, previewView, uiState.selectedCamera)

        // 4. El bloque onDispose se encarga de la limpieza cuando la pantalla desaparece
        onDispose {
            cameraViewModel.onScreenDisposed()
        }
    }

    fun onCameraClick(){
        if(uiState.cameraAction == CameraAction.VIDEO){
            cameraViewModel.onStartRecording()
        } else {
            Log.i("S","Sacando una photo")
            //sacar photo
        }
    }

    CameraScreen(
        uiState = uiState,
        onRecordClick = { onCameraClick() } ,
        onStopRecording = cameraViewModel::onStopRecording,
        onPhotoMode = cameraViewModel::onPhotoMode,
        onVideoMode = cameraViewModel::onVideoMode,
        onSwitchCamera = cameraViewModel::onSwitchCamera,
        cameraPreview = {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
        },
    )
}