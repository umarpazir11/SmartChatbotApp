package com.demo.smartchatbotapp.common

import com.demo.smartchatbotapp.data.local.entity.ChatMessageEntity
import com.demo.smartchatbotapp.domain.model.ChatMessage

fun ChatMessageEntity.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        content = content,
        isUser = isUser,
        timestamp = timestamp
    )
}

fun ChatMessage.toEntity(): ChatMessageEntity {
    return ChatMessageEntity(
        id = id,
        content = content,
        isUser = isUser,
        timestamp = timestamp
    )
} 