package com.example.allengineeringinone.ui.common.Chat.data.model

data class Message(
    val text: String,
    val sender: Sender,
    val timestamp: Long = System.currentTimeMillis()
)

enum class Sender {
    USER,
    BOT
}
