package com.demo.smartchatbotapp.domain.usecase

import com.demo.smartchatbotapp.domain.model.ChatMessage
import com.demo.smartchatbotapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving chat history.
 * This use case encapsulates the business logic for fetching chat messages.
 *
 * @property repository The chat repository instance
 */
class GetChatHistoryUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    /**
     * Invokes the use case to get the chat history.
     * Returns a Flow of message lists that will emit new values
     * whenever the chat history changes.
     *
     * @return A Flow emitting lists of [ChatMessage]
     */
    operator fun invoke(): Flow<List<ChatMessage>> {
        return repository.getChatHistory()
    }
} 