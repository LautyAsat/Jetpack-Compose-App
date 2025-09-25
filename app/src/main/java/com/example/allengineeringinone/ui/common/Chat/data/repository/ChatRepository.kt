package com.example.allengineeringinone.ui.common.Chat.data.repository

import com.example.allengineeringinone.ui.common.Chat.data.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getResponsesFor(question: String): Result<String?>

    suspend fun observeMessages(): Flow<String>
}