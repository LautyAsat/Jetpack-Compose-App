package com.example.allengineeringinone.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.allengineeringinone.ui.common.Battery.BatteryWidget
import com.example.allengineeringinone.ui.common.Chat.data.model.ChatUIState
import com.example.allengineeringinone.ui.home.data.model.HomeEvent
import com.example.allengineeringinone.ui.home.data.model.HomeUIState

/**
 * Información: HomeRoute es el punto de entrada stateful de la pantalla de inicio.
 *
 * @inject homeViewModel: Inyección del viewModel del home
 * @param openDrawer (evento) solicita abrir el open drawer
 * @param chatUIState: estado del chat
 * @param onToggleChat: abre y cierra el chat
 * @param onMessageChatSent (evento) envía el mensaje del chat
 * @param onTextFieldChanged (evento) cambia el texto tel textfield
 */
@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    chatUIState: ChatUIState,
    onToggleChat: () -> Unit,
    onMessageChatSent: () -> Unit,
    onTextFieldChanged: (String) -> Unit
){
    val uiState: HomeUIState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        homeViewModel.events.collect { event ->
            when (event) {
                is HomeEvent.LaunchIntent -> {
                    context.startActivity(event.intent)
                }
            }
        }
    }

    HomeScreen(
        uiState = uiState,
        callEngineeringCousil = homeViewModel::callEngineeringCousil,
        openDrawer = openDrawer,
        chatUIState = chatUIState,
        onToggleChat = onToggleChat,
        onMessageChatSent = onMessageChatSent,
        onTextFieldChanged = onTextFieldChanged
    )
}



