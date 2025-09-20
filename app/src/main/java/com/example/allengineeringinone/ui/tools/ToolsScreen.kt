package com.example.allengineeringinone.ui.tools

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun ToolsScreen(
    openDrawer: () -> Unit,
    uiState: ToolsUIState,
    onFlashLightClick: () -> Unit,
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

                Button(
                    onClick = { onFlashLightClick() },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.permissionCameraStatus == PermissionStatus.GRANTED) colorResource(R.color.primary) else colorResource(R.color.gray),
                        contentColor  = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = if (uiState.permissionCameraStatus == PermissionStatus.GRANTED) "Encender Linterna" else "Permiso de camara denegado",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Black,
                        fontSize = 18.sp
                    )
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    )
}