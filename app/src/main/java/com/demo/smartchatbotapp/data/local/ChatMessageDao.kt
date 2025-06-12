package com.demo.smartchatbotapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.demo.smartchatbotapp.domain.model.ChatMessage

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    suspend fun getAllMessages(): List<ChatMessage>

    @Insert
    suspend fun insertMessage(message: ChatMessage): Long

    @Query("DELETE FROM chat_messages")
    suspend fun clearChatHistory()
} 