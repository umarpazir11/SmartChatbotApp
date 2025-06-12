package com.demo.smartchatbotapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.smartchatbotapp.domain.model.ChatMessage
import com.demo.smartchatbotapp.domain.usecase.GetChatHistoryUseCase
import com.demo.smartchatbotapp.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _chatState = MutableStateFlow<ChatState>(ChatState.Loading)
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    init {
        loadChatHistory()
    }

    private fun loadChatHistory() {
        viewModelScope.launch {
            try {
                val messages = getChatHistoryUseCase()
                _chatState.value = ChatState.Success(messages)
            } catch (e: Exception) {
                _chatState.value = ChatState.Error(e.message ?: "Failed to load chat history")
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                sendMessageUseCase(message).fold(
                    onSuccess = { newMessage ->
                        val currentMessages = (_chatState.value as? ChatState.Success)?.messages ?: emptyList()
                        _chatState.value = ChatState.Success(currentMessages + newMessage)
                    },
                    onFailure = { error ->
                        _chatState.value = ChatState.Error(error.message ?: "Failed to send message")
                    }
                )
            } catch (e: Exception) {
                _chatState.value = ChatState.Error(e.message ?: "Failed to send message")
            }
        }
    }
}

sealed class ChatState {
    data object Loading : ChatState()
    data class Success(val messages: List<ChatMessage>) : ChatState()
    data class Error(val message: String) : ChatState()
} 