package com.example.allengineeringinone.ui.tools

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
import androidx.compose.ui.unit.dp
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


/**
* información: ToolsScreen es la vista stateless de la pantalla de herramientas.
*
* @param openDrawer: abre el drawer
* @param uiState: estado de la pantalla
* @param onFlashLightClick: click en el boton de la flash (Se encarga de ver si hay permisos o no y las funciones correspondientes)
* @param onStartRecording: click en el boton de la grabacion (Se encarga de ver si hay permisos o no y las funciones correspondientes)
* @param onStopRecording: detiene la grabación
* @param chatUIState: estado del chat
* @param onToggleChat: abre y cierra el chat
* @param onMessageChatSent: envía el mensaje del chat
* @param onTextFieldChanged: cambia el texto del textfield
* */

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