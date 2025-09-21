package com.example.allengineeringinone.ui.map

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allengineeringinone.R
import com.example.allengineeringinone.ui.common.TopAppBar.TopAppBar
import com.example.allengineeringinone.ui.common.components.FAB
import com.example.allengineeringinone.ui.map.data.model.MapUIState
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapScreen(
    uiState: MapUIState,
    cameraPositionState: CameraPositionState,
    openDrawer: () -> Unit,
    onAddMarkerClick: () -> Unit,
){

    Scaffold(
        topBar = {
            // Barra superior de la app.
            TopAppBar(openDrawer)
        },

        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
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

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        Log.i("map", "Click")
                        onAddMarkerClick()
                    },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.permissionStatus == PermissionStatus.GRANTED) colorResource(R.color.primary) else colorResource(R.color.gray),
                        contentColor  = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (uiState.permissionStatus == PermissionStatus.GRANTED) "Guardar mi punto actual" else "Permiso de ubicación denegado",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                }

                Spacer(Modifier.height(20.dp))
            }

        },

        floatingActionButton = { FAB() },
        floatingActionButtonPosition = FabPosition.EndOverlay
    )
}