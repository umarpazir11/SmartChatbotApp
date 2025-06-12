package com.demo.smartchatbotapp.data.repository

import com.demo.smartchatbotapp.data.local.dao.ChatMessageDao
import com.demo.smartchatbotapp.data.local.entity.ChatMessageEntity
import com.demo.smartchatbotapp.domain.model.ChatMessage
import com.demo.smartchatbotapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatMessageDao: ChatMessageDao
) : ChatRepository {

    override fun getChatHistory(): Flow<List<ChatMessage>> {
        return chatMessageDao.getChatMessages().map { entities ->
            entities.map { it.toChatMessage() }
        }
    }

    override suspend fun sendMessage(message: String): ChatMessage {
        val chatMessage = ChatMessage(
            id = UUID.randomUUID().toString(),
            content = message,
            isFromUser = true
        )
        chatMessageDao.insertMessage(chatMessage.toEntity())
        return chatMessage
    }

    override suspend fun clearChatHistory() {
        chatMessageDao.clearChatHistory()
    }

    private fun ChatMessageEntity.toChatMessage() = ChatMessage(
        id = id,
        content = content,
        isFromUser = isFromUser,
        timestamp = timestamp
    )

    private fun ChatMessage.toEntity() = ChatMessageEntity(
        id = id,
        content = content,
        isFromUser = isFromUser,
        timestamp = timestamp
    )
} 