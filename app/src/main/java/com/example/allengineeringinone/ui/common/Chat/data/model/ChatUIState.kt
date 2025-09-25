package com.example.allengineeringinone.ui.common.Chat.data.model

data class ChatUIState(
    val isChatOpen: Boolean = false,
    val currentText: String = "",
    val listMessage: List<Message> = listOf(
        Message(
            text = "Hola, como estás? Soy un bot virtual, me llamo Larti, en que puedo ayudarte?",
            sender = Sender.BOT,
            timestamp = System.currentTimeMillis()
        )
    ),
    val isBotResponding: Boolean = true
)
