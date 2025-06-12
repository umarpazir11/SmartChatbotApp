package com.demo.smartchatbotapp.data.repository

import com.demo.smartchatbotapp.common.toDomain
import com.demo.smartchatbotapp.data.local.dao.ChatMessageDao
import com.demo.smartchatbotapp.data.local.entity.ChatMessageEntity
import com.demo.smartchatbotapp.data.remote.ChatApiService
import com.demo.smartchatbotapp.data.remote.model.ChatRequest
import com.demo.smartchatbotapp.data.remote.model.Message
import com.demo.smartchatbotapp.domain.model.ChatMessage
import com.demo.smartchatbotapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val dao: ChatMessageDao,
    private val api: ChatApiService
) : ChatRepository {

    override fun getChatHistory(): Flow<List<ChatMessage>> {
        return dao.getAllMessages().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun sendMessage(message: String): Result<ChatMessage> {
        return try {
            // Save user message
            val userMessageId = dao.insertMessage(
                ChatMessageEntity(
                    content = message,
                    isUser = true
                )
            )

            // Send to API
            val response = api.sendMessage(
                ChatRequest(
                    model = "deepseek-chat",
                    messages = listOf(Message(role = "user", content = message))
                )
            )

            // Save bot response
            val botMessage = response.choices.firstOrNull()?.message
            if (botMessage != null) {
                val botMessageId = dao.insertMessage(
                    ChatMessageEntity(
                        content = botMessage.content,
                        isUser = false
                    )
                )
                Result.success(ChatMessage(
                    id = botMessageId,
                    content = botMessage.content,
                    isUser = false
                ))
            } else {
                Result.failure(Exception("No response from API"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearChatHistory() {
        dao.clearChatHistory()
    }

    private fun ChatMessageEntity.toDomain(): ChatMessage {
        return ChatMessage(
            id = id,
            content = content,
            isUser = isUser,
            timestamp = timestamp
        )
    }
} 