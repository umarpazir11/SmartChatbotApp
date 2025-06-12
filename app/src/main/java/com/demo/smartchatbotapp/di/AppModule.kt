package com.demo.smartchatbotapp.di

import android.app.Application
import androidx.room.Room
import com.demo.smartchatbotapp.data.local.ChatDatabase
import com.demo.smartchatbotapp.data.local.dao.ChatMessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideChatDatabase(app: Application): ChatDatabase {
        return Room.databaseBuilder(
            app,
            ChatDatabase::class.java,
            "chat_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideChatMessageDao(db: ChatDatabase): ChatMessageDao {
        return db.chatMessageDao
    }
} 