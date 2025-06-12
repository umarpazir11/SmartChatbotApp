package com.demo.smartchatbotapp.domain.repository

import com.demo.smartchatbotapp.domain.model.ChatMessage

interface ChatRepository {
    suspend fun getChatHistory(): List<ChatMessage>
    suspend fun sendMessage(message: String): Result<ChatMessage>
    suspend fun clearChatHistory() // Add this line
} 