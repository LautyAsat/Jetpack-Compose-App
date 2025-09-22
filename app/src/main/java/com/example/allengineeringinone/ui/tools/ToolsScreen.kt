package com.example.allengineeringinone.ui.tools

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allengineeringinone.ui.common.TopAppBar.TopAppBar
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.example.allengineeringinone.ui.tools.data.model.ToolsUIState
import com.example.allengineeringinone.R
import com.example.allengineeringinone.ui.common.components.AudioRecorderWidget
import com.example.allengineeringinone.ui.common.components.FAB
import com.example.allengineeringinone.ui.common.components.PrimaryButton

@Composable
fun ToolsScreen(
    openDrawer: () -> Unit,
    uiState: ToolsUIState,
    onFlashLightClick: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(openDrawer)
        },

        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                Spacer(Modifier.height(20.dp))

                PrimaryButton(
                    onClick = { onFlashLightClick() },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    text = "Encender Linterna",
                    textUnabled = "Permiso de camara denegado",
                    enabled = uiState.permissionCameraStatus == PermissionStatus.GRANTED
                )

                Spacer(Modifier.height(20.dp))

                AudioRecorderWidget(
                    uiState = uiState,
                    onStartRecording = onStartRecording,
                    onStopRecording = onStopRecording
                )
            }
        },

        floatingActionButton = { FAB() },
        floatingActionButtonPosition = FabPosition.EndOverlay
    )
}