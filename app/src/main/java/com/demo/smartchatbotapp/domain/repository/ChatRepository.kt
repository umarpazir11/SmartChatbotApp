package com.demo.smartchatbotapp.domain.repository

import com.demo.smartchatbotapp.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for chat-related operations.
 * This interface defines the contract for chat data operations in the domain layer.
 */
interface ChatRepository {
    /**
     * Retrieves the chat history as a Flow of message lists.
     * The Flow will emit new values whenever the chat history changes.
     *
     * @return A Flow emitting lists of [ChatMessage]
     */
    fun getChatHistory(): Flow<List<ChatMessage>>

    /**
     * Sends a message to the chat and returns the bot's response.
     * This operation is asynchronous and returns a [Result] containing either
     * the bot's response message or an error.
     *
     * @param message The message text to send
     * @return A [Result] containing either a [ChatMessage] or an error
     */
    suspend fun sendMessage(message: String): Result<ChatMessage>

    suspend fun clearChatHistory()
} 