package com.example.allengineeringinone.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allengineeringinone.R
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.example.allengineeringinone.ui.tools.data.model.ToolsUIState

@Composable
fun AudioRecorderWidget(
    uiState: ToolsUIState,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    modifier: Modifier = Modifier
){

    Box(
    modifier = modifier
    .fillMaxWidth()
    .background(Color.White, RoundedCornerShape(16.dp))
    .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
    .padding(16.dp)
    ){
        Column(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Grabadora de audio",
                color = colorResource(id = R.color.dark_gray),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            if(uiState.permissionAudioStatus == PermissionStatus.GRANTED){
                Row (Modifier.fillMaxWidth()){
                    PrimaryButton(modifier = Modifier
                        .weight(1f),
                        onClick = { onStartRecording() },
                        text = "Grabar",
                        textUnabled = "Grabando...",
                        enabled = !uiState.isRecording
                    )

                    Spacer(Modifier.width(8.dp))

                    PrimaryButton(modifier = Modifier
                        .weight(1f)
                        ,
                        onClick = { onStopRecording() },
                        text = "Parar",
                        textUnabled = "Parar",
                        enabled = uiState.isRecording
                    )
                }

                Spacer(Modifier.height(16.dp))

                if(uiState.isRecording){
                    Text("La grabación ha iniciado.", color = colorResource(R.color.red))
                }
            }
            else{
                Text("Permiso de audio denegado", color = colorResource(R.color.red), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }

        }


    }
}