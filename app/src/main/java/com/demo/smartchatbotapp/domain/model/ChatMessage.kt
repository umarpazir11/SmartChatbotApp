package com.demo.smartchatbotapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a chat message in the domain layer.
 *
 * @property id Unique identifier for the message
 * @property content The text content of the message
 * @property isUser Whether the message is from the user (true) or the bot (false)
 * @property timestamp When the message was created (in milliseconds since epoch)
 */
@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
) 