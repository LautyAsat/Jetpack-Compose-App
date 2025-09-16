package com.example.allengineeringinone.ui.tools

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraControl
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.common.Battery.BatteryWidget
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.example.allengineeringinone.ui.tools.data.model.CameraAction
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
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        toolsViewModel.onPermissionResult(isGranted)
    }


    // Laucher para pedir permiso
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            toolsViewModel.onPermissionResult(isGranted)

            if (isGranted) {
                when (uiState.cameraAction) {
                    CameraAction.FLASH -> toolsViewModel.onFlashToggle()
                    else -> {}
                }
            }
        }
    )

    val onRequestPermission: () -> Unit = {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }


    // handlers

    val onFlashLightClick: () -> Unit = {
        if (uiState.permissionStatus == PermissionStatus.GRANTED)
            toolsViewModel.onFlashToggle()
        else
            onRequestPermission()
            toolsViewModel.onCameraAction(CameraAction.FLASH)
    }

    ToolsScreen(
        openDrawer,
        uiState = uiState,
        batteryWidget = { modifier -> BatteryWidget(modifier) },
        handleFlashLightClick = onFlashLightClick

    )
}