package com.example.allengineeringinone.ui.tools

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.common.Battery.BatteryWidget
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.google.accompanist.permissions.ExperimentalPermissionsApi
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ToolsRoute(
    openDrawer: () -> Unit,
    toolsViewModel: ToolsViewModel = hiltViewModel()
){
    val uiState by toolsViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Verificamos si ya tenemos los permisos
    LaunchedEffect(Unit) {

        Log.i("information", "Paso por aca");

        val isCameraGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        toolsViewModel.onPermissionsResult(mapOf(
            Manifest.permission.CAMERA to isCameraGranted,
        ))
    }

    // --- LAUNCHER #1: SOLO PARA LA CÁMARA (LINTERNA) ---
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            toolsViewModel.onPermissionsResult(mapOf(Manifest.permission.CAMERA to isGranted))
        }
    )

    fun onFlashLightClick(){
        if(uiState.permissionCameraStatus == PermissionStatus.GRANTED){
            toolsViewModel.onFlashToggle()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    ToolsScreen(
        openDrawer,
        uiState = uiState,
        onFlashLightClick = { onFlashLightClick() },
        onStartRecording = { toolsViewModel.onStartRecording() },
        onStopRecording = { toolsViewModel.onStopRecording() }
    )
}