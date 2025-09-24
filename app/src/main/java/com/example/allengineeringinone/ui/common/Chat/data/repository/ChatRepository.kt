package com.example.allengineeringinone.ui.common.Chat.data.repository

interface ChatRepository {
    suspend fun getResponsesFor(question: String): Result<String?>
}