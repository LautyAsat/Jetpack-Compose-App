package com.example.allengineeringinone.ui.map

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.allengineeringinone.ui.components.TopAppBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    uiState: MapUIState,
    cameraPositionState: CameraPositionState,
    openDrawer: () -> Unit,
    batteryWidget: @Composable (Modifier) -> Unit
){

    Scaffold(
        topBar = {
            // Barra superior de la app.
            TopAppBar(openDrawer, batteryWidget)
        },

        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text("Agrega tu propio marcador")

                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                    ,
                    cameraPositionState = cameraPositionState
                ) {

                    uiState.markers.forEach { mapMarker ->
                        mapMarker.location?.let { geoPoint ->
                            val position = LatLng(geoPoint.latitude, geoPoint.longitude) // <-- Extraes los valores
                            Marker(
                                state = MarkerState(position = position),
                                title = mapMarker.name
                            )
                        }
                    }
                }

            }


        }
    )
}