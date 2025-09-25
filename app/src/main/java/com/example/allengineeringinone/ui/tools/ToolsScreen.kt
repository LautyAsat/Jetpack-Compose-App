package com.example.allengineeringinone.ui.tools

import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.common.TopAppBar.TopAppBar
import com.example.allengineeringinone.ui.map.data.model.PermissionStatus
import com.example.allengineeringinone.ui.tools.data.model.ToolsUIState
import com.example.allengineeringinone.ui.common.Chat.ChatViewModel
import com.example.allengineeringinone.ui.common.Chat.ChatWidget
import com.example.allengineeringinone.ui.common.Chat.data.model.ChatUIState
import com.example.allengineeringinone.ui.common.Chat.data.model.Message
import com.example.allengineeringinone.ui.common.Chat.data.model.Sender
import com.example.allengineeringinone.ui.common.components.AudioRecorderWidget
import com.example.allengineeringinone.ui.common.components.FAB
import com.example.allengineeringinone.ui.common.components.PrimaryButton
import com.google.android.material.bottomsheet.BottomSheetDragHandleView
import kotlin.math.absoluteValue
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsScreen(
    openDrawer: () -> Unit,
    uiState: ToolsUIState,
    onFlashLightClick: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    chatUIState: ChatUIState,
    onToggleChat: () -> Unit,
    onMessageChatSent: () -> Unit,
    onTextFieldChanged: (String) -> Unit
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                        , horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(color = Color(0xFF1B5E20),
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Normal,
                        text = "Nivel"
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f),
                        color = Color(0xFF4CAF50),
                        fontSize = 150.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        text = "${uiState.inclination.toInt()}"
                    )
                }

            }


            ChatWidget(
                uiState = chatUIState,
                paddingValues = paddingValues,
                onToggleChat = onToggleChat,
                onSendMessage = onMessageChatSent,
                onTextFieldChanged = onTextFieldChanged
            )
        },

        floatingActionButton = { FAB { onToggleChat() } },
        floatingActionButtonPosition = FabPosition.EndOverlay
    )
}