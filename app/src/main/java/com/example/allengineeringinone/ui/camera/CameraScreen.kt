package com.example.allengineeringinone.ui.camera

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.filled.Camera
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.EmergencyRecording
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.allengineeringinone.ui.camera.data.model.CameraAction
import com.example.allengineeringinone.ui.camera.data.model.CameraUIState

@Composable
fun CameraScreen(
    uiState: CameraUIState,
    onRecordClick: () -> Unit,
    onStopRecording: () -> Unit,
    onPhotoMode: () -> Unit,
    onVideoMode: () -> Unit,
    cameraPreview: @Composable () -> Unit
){

    val infiniteTransition = rememberInfiniteTransition(label = "recording_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.8f, // Hacemos que se encoja un poco para el efecto de pulso
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    cameraPreview()

    Scaffold(
        containerColor = Color.Transparent,
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)){  }
        },
        topBar = {
            if (uiState.isRecording){
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    IconButton(
                        onClick = onStopRecording,
                        modifier = Modifier.scale(scale)
                            .padding(16.dp)
                            .background(Color.White, shape = CircleShape),
                    )
                    {
                        Icon(Icons.Filled.FiberManualRecord, contentDescription = "Parar", tint = Color.Red)
                    }
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth().height(300.dp
                ),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    IconButton(
                        onClick = onRecordClick,
                        modifier = Modifier.padding(16.dp).background(Color.Black, shape = CircleShape).size(70.dp),
                    )
                    {
                        Icon(Icons.Filled.Camera, contentDescription = "Grabar", tint = Color.White, modifier = Modifier.size(50.dp))
                    }

                    if(uiState.cameraAction == CameraAction.VIDEO){
                        IconButton(
                            onClick = onStopRecording,
                            modifier = Modifier.padding(16.dp).background(Color.Black, shape = CircleShape).size(70.dp),
                        )
                        {
                            Icon(Icons.Filled.Stop, contentDescription = "Parar", tint = if(uiState.isRecording) Color.Green else Color.White, modifier = Modifier.size(50.dp))
                        }
                    }

                }

                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                        IconButton(
                            onClick = onPhotoMode,
                            modifier = Modifier.padding(16.dp).background(if (uiState.cameraAction == CameraAction.PHOTO) Color.Red else Color.Black, shape = CircleShape),
                        )
                        {
                            Icon(Icons.Filled.CameraAlt, contentDescription = "Photo", tint = Color.White, modifier = Modifier.size(30.dp))
                        }
                        IconButton(
                            onClick = onVideoMode,
                            modifier = Modifier.padding(16.dp).background(if (uiState.cameraAction == CameraAction.VIDEO) Color.Red else Color.Black, shape = CircleShape),
                        )
                        {
                            Icon(Icons.Filled.EmergencyRecording, contentDescription = "Grabar", tint = Color.White, modifier = Modifier.size(30.dp))
                        }
                }
            }
        }

    )
}