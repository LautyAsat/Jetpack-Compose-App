package com.example.allengineeringinone.ui.map

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.home.battery.BatteryRoute
import com.example.allengineeringinone.ui.home.battery.BatteryViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.rememberCameraPositionState

@ExperimentalMaterial3Api
@Composable
fun MapRoute(
    batteryViewModel: BatteryViewModel,
    openDrawer: () -> Unit,
    mapViewModel: MapViewModel = hiltViewModel()
){
    val uiState by mapViewModel.uiState.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                mapViewModel.fetchInitialLocation()
            }

        }
    )

    LaunchedEffect (Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    val cameraPositionState = rememberCameraPositionState()

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
        batteryWidget = { modifier -> BatteryRoute(batteryViewModel, modifier) },
    )
}