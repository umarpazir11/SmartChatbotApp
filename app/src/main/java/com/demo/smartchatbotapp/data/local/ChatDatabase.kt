package com.demo.smartchatbotapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.smartchatbotapp.data.local.dao.ChatMessageDao
import com.demo.smartchatbotapp.data.local.entity.ChatMessageEntity

@Database(
    entities = [ChatMessageEntity::class],
    version = 1
)
abstract class ChatDatabase : RoomDatabase() {
    abstract val chatMessageDao: ChatMessageDao
} 