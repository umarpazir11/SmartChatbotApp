package com.demo.smartchatbotapp.domain.repository

import com.demo.smartchatbotapp.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChatHistory(): Flow<List<ChatMessage>>
    suspend fun sendMessage(message: String): ChatMessage
    suspend fun clearChatHistory()
} 