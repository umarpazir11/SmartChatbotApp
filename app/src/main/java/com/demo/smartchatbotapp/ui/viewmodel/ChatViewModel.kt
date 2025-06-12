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

/**
 * ViewModel for managing chat-related UI state and operations.
 * This ViewModel handles the business logic for the chat screen,
 * including loading chat history and sending messages.
 *
 * @property getChatHistoryUseCase Use case for retrieving chat history
 * @property sendMessageUseCase Use case for sending messages
 */
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

    /**
     * Loads the chat history using the [GetChatHistoryUseCase].
     * Updates the [chatState] with the results.
     */
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

    /**
     * Sends a message using the [SendMessageUseCase].
     * Updates the [chatState] based on the result.
     *
     * @param content The message content to send
     */
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

/**
 * Represents the possible states of the chat UI.
 */
sealed class ChatState {
    /**
     * Indicates that a loading operation is in progress.
     */
    data object Loading : ChatState()

    /**
     * Indicates that the operation was successful.
     *
     * @property messages The list of chat messages
     */
    data class Success(val messages: List<ChatMessage>) : ChatState()

    /**
     * Indicates that an error occurred.
     *
     * @property message The error message
     */
    data class Error(val message: String) : ChatState()
} 