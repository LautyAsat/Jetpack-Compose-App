package com.example.allengineeringinone.ui.common.Chat.data.model

import androidx.compose.runtime.staticCompositionLocalOf


/**
 * Agrupa todas las funciones de callback (eventos) para el Chat.
 * Esto simplifica pasarlas eliminando el propDrilling
 */
data class ChatActions(
    val onToggleChat: () -> Unit = {},
    val onMessageChatSent: () -> Unit = {},
    val onTextFieldChanged: (String) -> Unit = {}
)



/**
 * CompositionLocal para proveer las ChatActions de forma implícita
 * a través del árbol de Composables.
 * * -> ¡Este es el lugar correcto para esta línea!
 */
val LocalChatActions = staticCompositionLocalOf<ChatActions> {
    error("Error: No ChatActions provided. Asegúrate de usar CompositionLocalProvider.")
}

