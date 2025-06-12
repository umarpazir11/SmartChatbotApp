package com.demo.smartchatbotapp.data.repository

import com.demo.smartchatbotapp.data.local.dao.ChatMessageDao
import com.demo.smartchatbotapp.data.local.entity.ChatMessageEntity
import com.demo.smartchatbotapp.data.remote.ChatApiService
import com.demo.smartchatbotapp.data.remote.ChatRequest
import com.demo.smartchatbotapp.data.remote.Message
import com.demo.smartchatbotapp.domain.model.ChatMessage
import com.demo.smartchatbotapp.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val dao: ChatMessageDao,
    private val api: ChatApiService
) : ChatRepository {

    override suspend fun getChatHistory(): List<ChatMessage> {
        return dao.getAllMessages().map { it.toDomain() }
    }

    override suspend fun sendMessage(message: String): Result<ChatMessage> {
        return try {
            // Save user message
            val userMessage = ChatMessageEntity(
                content = message,
                isUser = true
            )
            dao.insertMessage(userMessage)

            // Send to API
            val response = api.sendMessage(
                ChatRequest(
                    messages = listOf(
                        Message(role = "user", content = message)
                    )
                )
            )

            // Save bot response
            val botMessage = ChatMessageEntity(
                content = response.choices.firstOrNull()?.message?.content ?: "No response",
                isUser = false
            )
            dao.insertMessage(botMessage)

            Result.success(botMessage.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearChatHistory() {
        dao.clearChatHistory()
    }

    private fun ChatMessageEntity.toDomain() = ChatMessage(
        id = id,
        content = content,
        isUser = isUser,
        timestamp = timestamp
    )
} 