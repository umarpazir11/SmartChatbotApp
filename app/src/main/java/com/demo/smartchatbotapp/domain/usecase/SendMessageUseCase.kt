package com.demo.smartchatbotapp.domain.usecase

import com.demo.smartchatbotapp.domain.model.ChatMessage
import com.demo.smartchatbotapp.domain.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(message: String): ChatMessage {
        return repository.sendMessage(message)
    }
} 