package com.demo.smartchatbotapp.di

import com.demo.smartchatbotapp.ui.viewmodel.ChatViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import com.demo.smartchatbotapp.domain.usecase.GetChatHistoryUseCase
import com.demo.smartchatbotapp.domain.usecase.SendMessageUseCase

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideChatViewModel(
        getChatHistoryUseCase: GetChatHistoryUseCase,
        sendMessageUseCase: SendMessageUseCase
    ): ChatViewModel {
        return ChatViewModel(
            getChatHistoryUseCase = getChatHistoryUseCase,
            sendMessageUseCase = sendMessageUseCase
        )
    }
} 