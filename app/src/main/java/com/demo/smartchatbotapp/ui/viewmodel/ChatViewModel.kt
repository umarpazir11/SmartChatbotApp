package com.demo.smartchatbotapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.smartchatbotapp.domain.model.ChatMessage
import com.demo.smartchatbotapp.domain.usecase.GetChatHistoryUseCase
import com.demo.smartchatbotapp.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _chatState = MutableStateFlow<ChatState>(ChatState.Loading)
    val chatState: StateFlow<ChatState> = _chatState

    init {
        loadChatHistory()
    }

    private fun loadChatHistory() {
        getChatHistoryUseCase()
            .onEach { messages ->
                _chatState.value = ChatState.Success(messages)
            }
            .catch { error ->
                _chatState.value = ChatState.Error(error.message ?: "Unknown error occurred")
            }
            .launchIn(viewModelScope)
    }

    fun sendMessage(content: String) {
        viewModelScope.launch {
            _chatState.value = ChatState.Loading
            sendMessageUseCase(content)
                .onSuccess { message ->
                    // The chat history will be updated automatically through Flow
                }
                .onFailure { error ->
                    _chatState.value = ChatState.Error(error.message ?: "Failed to send message")
                }
        }
    }
}

sealed class ChatState {
    data object Loading : ChatState()
    data class Success(val messages: List<ChatMessage>) : ChatState()
    data class Error(val message: String) : ChatState()
} 