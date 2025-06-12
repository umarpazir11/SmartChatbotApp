package com.demo.smartchatbotapp.util

import com.demo.smartchatbotapp.data.local.entity.ChatMessageEntity
import com.demo.smartchatbotapp.data.remote.model.ChatRequest
import com.demo.smartchatbotapp.data.remote.model.ChatResponse
import com.demo.smartchatbotapp.data.remote.model.Message
import com.demo.smartchatbotapp.domain.model.ChatMessage

object TestData {
    fun createChatMessage(
        id: Long = 1L,
        content: String = "Test message",
        isUser: Boolean = true,
        timestamp: Long = System.currentTimeMillis()
    ) = ChatMessage(
        id = id,
        content = content,
        isUser = isUser,
        timestamp = timestamp
    )

    fun createChatMessageEntity(
        id: Long = 1L,
        content: String = "Test message",
        isUser: Boolean = true,
        timestamp: Long = System.currentTimeMillis()
    ) = ChatMessageEntity(
        id = id,
        content = content,
        isUser = isUser,
        timestamp = timestamp
    )

    fun createChatRequest(
        model: String = "deepseek-chat",
        messages: List<Message> = listOf(Message(role = "user", content = "Test message"))
    ) = ChatRequest(
        model = model,
        messages = messages
    )

    fun createChatResponse(
        id: String = "chatcmpl-123",
        objectType: String = "chat.completion",
        created: Long = System.currentTimeMillis(),
        model: String = "deepseek-chat",
        choices: List<com.demo.smartchatbotapp.data.remote.model.Choice> = listOf(
            com.demo.smartchatbotapp.data.remote.model.Choice(
                index = 0,
                message = Message(role = "assistant", content = "Test response"),
                finishReason = "stop"
            )
        ),
        usage: com.demo.smartchatbotapp.data.remote.model.Usage = com.demo.smartchatbotapp.data.remote.model.Usage(
            promptTokens = 10,
            completionTokens = 20,
            totalTokens = 30
        )
    ) = ChatResponse(
        id = id,
        objectType = objectType,
        created = created,
        model = model,
        choices = choices,
        usage = usage
    )
} 