package com.demo.smartchatbotapp.di

import com.demo.smartchatbotapp.domain.usecase.GetChatHistoryUseCase
import com.demo.smartchatbotapp.domain.usecase.SendMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetChatHistoryUseCase(
        repository: com.demo.smartchatbotapp.domain.repository.ChatRepository
    ): GetChatHistoryUseCase {
        return GetChatHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSendMessageUseCase(
        repository: com.demo.smartchatbotapp.domain.repository.ChatRepository
    ): SendMessageUseCase {
        return SendMessageUseCase(repository)
    }
} 