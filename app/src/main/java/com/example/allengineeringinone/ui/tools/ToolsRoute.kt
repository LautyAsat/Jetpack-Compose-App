package com.example.allengineeringinone.ui.tools

import android.Manifest
import android.content.pm.PackageManager
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

    ToolsScreen(
        openDrawer,
        uiState = uiState,
        onFlashLightClick = toolsViewModel::onFlashToggle,
    )
}