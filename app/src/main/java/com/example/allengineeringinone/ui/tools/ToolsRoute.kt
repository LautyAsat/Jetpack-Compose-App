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
import com.example.allengineeringinone.ui.common.Chat.data.model.ChatUIState
import com.example.allengineeringinone.ui.common.Chat.data.model.LocalChatActions
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.google.accompanist.permissions.ExperimentalPermissionsApi


/**
* Información: ToolsRoute es el punto de entrada stateful de la pantalla de herramientas.
*
* @param openDrawer: abre el drawer
* @inject toolsViewModel: Inyección del viewModel del tools
* @param chatUIState: estado del chat
* */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ToolsRoute(
    openDrawer: () -> Unit,
    toolsViewModel: ToolsViewModel = hiltViewModel(),
    chatUIState: ChatUIState
){
    val uiState by toolsViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val chatActions = LocalChatActions.current

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsMap ->
            toolsViewModel.onPermissionsResult(permissionsMap)
        }
    )

    // Verificamos si ya tenemos los permisos
    LaunchedEffect(Unit) {
        val isCameraGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val isAudioGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        toolsViewModel.onPermissionsResult(mapOf(
            Manifest.permission.CAMERA to isCameraGranted, Manifest.permission.RECORD_AUDIO to isAudioGranted
        ))
    }

    fun onFlashLightClick(){
        if(uiState.permissionCameraStatus == PermissionStatus.GRANTED){
            toolsViewModel.onFlashToggle()
        } else {
            permissionsLauncher.launch(arrayOf(Manifest.permission.CAMERA))
        }
    }

    fun onRecordClick(){
        if(uiState.permissionAudioStatus == PermissionStatus.GRANTED){
            toolsViewModel.onStartRecording()
        } else {
            permissionsLauncher.launch(arrayOf(Manifest.permission.RECORD_AUDIO))
        }
    }

    ToolsScreen(
        openDrawer,
        uiState = uiState,
        onFlashLightClick = { onFlashLightClick() },
        onStartRecording = { onRecordClick() },
        onStopRecording = { toolsViewModel.onStopRecording() },
        chatUIState = chatUIState,
        chatActions = chatActions
    )
}