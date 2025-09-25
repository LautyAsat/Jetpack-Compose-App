package com.example.allengineeringinone.ui.map

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.common.Battery.BatteryWidget
import com.example.allengineeringinone.ui.common.Chat.data.model.ChatUIState
import com.example.allengineeringinone.ui.common.Chat.data.model.LocalChatActions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.rememberCameraPositionState


/**
 * Información: MapRoute es el punto de entrada stateful de la pantalla de mapas.
 *
 * @param openDrawer: abre el drawer
 * @inject mapViewModel: Inyección del viewModel del map
 * @param chatUIState: estado del chat
 * */
@ExperimentalMaterial3Api
@Composable
fun MapRoute(
    openDrawer: () -> Unit,
    mapViewModel: MapViewModel = hiltViewModel(),
    chatUIState: ChatUIState,
){
    val uiState by mapViewModel.uiState.collectAsStateWithLifecycle()
    val chatActions = LocalChatActions.current


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->

            //informamos al viewModel
            mapViewModel.onPermissionResult(isGranted)
        }
    )

    // Pedimos los permisos
    LaunchedEffect (Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    val cameraPositionState = rememberCameraPositionState()

    // Animación
    LaunchedEffect(uiState.initialLocation) {
        uiState.initialLocation?.let { location ->
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(location, 15f),
                durationMs = 1000
            )
        }
    }

    MapScreen(
        uiState = uiState,
        cameraPositionState = cameraPositionState,
        openDrawer = openDrawer,
        onAddMarkerClick = { mapViewModel.addMarker() },
        chatUIState = chatUIState,
        chatActions = chatActions
    )
}