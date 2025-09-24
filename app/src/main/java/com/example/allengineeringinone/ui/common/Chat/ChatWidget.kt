package com.example.allengineeringinone.ui.common.Chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.allengineeringinone.ui.common.Chat.data.model.ChatUIState
import com.example.allengineeringinone.ui.common.Chat.data.model.Message
import com.example.allengineeringinone.ui.common.Chat.data.model.Sender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatWidget(
    uiState: ChatUIState,
    onTextFieldChanged: (String) -> Unit,
    onSendMessage: () -> Unit,
    onToggleChat: () -> Unit,
    paddingValues: PaddingValues
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val lazyListState = rememberLazyListState()

    LaunchedEffect(uiState.isChatOpen) {
        if (uiState.isChatOpen) sheetState.show() else sheetState.hide()
    }

    LaunchedEffect(uiState.listMessage.size) {
        lazyListState.animateScrollToItem(index = 0)
    }


    if(uiState.isChatOpen){
        ModalBottomSheet (
            onDismissRequest = { onToggleChat() },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets =  { WindowInsets(0.dp) },
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8f)
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    reverseLayout = true
                ) {

                    items(items = uiState.listMessage.reversed(), key = { it.timestamp } ){ ChatMessageBubble(message = it) }
                }

                // La barra de entrada de texto
                MessageInputBar(
                    text = uiState.currentText,
                    onTextChanged = { newText ->
                        onTextFieldChanged(newText)
                    },
                    onSendClick = {
                        onSendMessage()
                    }
                )
            }
        }
    }

}


/**
 * Dibuja una única burbuja de mensaje, alineada a la derecha (USER) o izquierda (BOT).
 */
@Composable
fun ChatMessageBubble(message: Message) {
    val isUserMessage = message.sender == Sender.USER

    // Alineamos la fila completa a la derecha o izquierda
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start
    ) {
        // La burbuja del mensaje
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (isUserMessage) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.widthIn(max = 300.dp) // Ancho máximo para la burbuja
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = if (isUserMessage) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * La barra inferior con el campo de texto y el botón de enviar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInputBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface (
        tonalElevation = 4.dp, // Una pequeña sombra para separarla del contenido
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                placeholder = { Text("Escribe un mensaje...") },
            )
            IconButton(
                onClick = { onSendClick() },
                enabled = text.isNotBlank(),
                modifier = Modifier.background(Color.White)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Enviar",
                    tint = Color.Black
                )
            }
        }
    }
}