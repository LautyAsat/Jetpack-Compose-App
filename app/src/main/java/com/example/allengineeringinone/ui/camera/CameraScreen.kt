package com.example.allengineeringinone.ui.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.filled.Camera
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.allengineeringinone.ui.camera.data.model.CameraUIState

@Composable
fun CameraScreen(
    uiState: CameraUIState,
    onRecordClick: () -> Unit,
    onStopRecording: () -> Unit,
    cameraPreview: @Composable () -> Unit
){
    cameraPreview()

    Scaffold(
        containerColor = Color.Transparent,
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)){  }
        },
        bottomBar = {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){

                IconButton(
                    onClick = onRecordClick,
                    modifier = Modifier.padding(16.dp).background(Color.Black, shape = CircleShape),
                )
                {
                    Icon(Icons.Filled.Camera, contentDescription = "Grabar", tint = Color.White)
                }

                IconButton(
                    onClick = onStopRecording,
                    modifier = Modifier.padding(16.dp).background(Color.Black, shape = CircleShape),
                )
                {
                    Icon(Icons.Filled.Stop, contentDescription = "Parar", tint = Color.White)
                }
            }
        }

    )
}