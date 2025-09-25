package com.example.allengineeringinone.ui.common.Chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allengineeringinone.ui.common.Chat.data.model.ChatUIState
import com.example.allengineeringinone.ui.common.Chat.data.model.Message
import com.example.allengineeringinone.ui.common.Chat.data.model.Sender
import com.example.allengineeringinone.ui.common.Chat.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val viewModelState = MutableStateFlow(ChatUIState())
    val uiState = viewModelState.asStateFlow()


    init {
        viewModelScope.launch {
            chatRepository.observeMessages().collect { newMessage ->

                // Observamos lo que escribe el operador en caso de que el bot ya no sea el que tenga el control.
                if(!uiState.value.isBotResponding){
                    addMessageToChat(newMessage, Sender.BOT)
                }
            }
        }
    }

    fun toggleChat(){
        viewModelState.update { it.copy( isChatOpen = !it.isChatOpen) }
    }

    fun onTextFieldChanged(newText: String) {
        viewModelState.update { it.copy(currentText = newText) }
    }

    fun onSendMessage(){

        val userMessageText = viewModelState.value.currentText
        viewModelState.update { it.copy(currentText = "") }

        Log.i("DEBUG", userMessageText)

        if(userMessageText.isBlank()) return

        addMessageToChat(userMessageText, Sender.USER)


        // El bot responde solo si es él que lleva la conversación
        if(uiState.value.isBotResponding){
            viewModelScope.launch {
                chatRepository.getResponsesFor(userMessageText)
                    .onSuccess { response ->
                        val botText = response ?: ""

                        if(response?.isNotBlank() == true){
                            addMessageToChat(botText, Sender.BOT)
                        }
                        else{
                            addMessageToChat("Perdón por no poder ayudarte, se te asignara un operador en los proximos minutos.", Sender.BOT)
                            viewModelState.update { it.copy( isBotResponding = false) }
                        }
                    }
                    .onFailure {
                        Log.e("CHAT_DEBUG", "Error en el chat", it)
                    }
            }
        }
    }

    private fun addMessageToChat(text: String, sender: Sender){
        val message = Message(text, sender)

        Log.i("DEBUG", message.toString())

        viewModelState.update { currentState ->
            currentState.copy(listMessage = currentState.listMessage + message)
        }

    }
}