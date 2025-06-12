package com.demo.smartchatbotapp.di

import com.demo.smartchatbotapp.data.local.dao.ChatMessageDao
import com.demo.smartchatbotapp.data.remote.ChatApiService
import com.demo.smartchatbotapp.data.repository.ChatRepositoryImpl
import com.demo.smartchatbotapp.domain.repository.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    companion object {
        @Provides
        @Singleton
        fun provideChatRepositoryImpl(
            dao: ChatMessageDao,
            api: ChatApiService
        ): ChatRepositoryImpl {
            return ChatRepositoryImpl(dao, api)
        }
    }
} 