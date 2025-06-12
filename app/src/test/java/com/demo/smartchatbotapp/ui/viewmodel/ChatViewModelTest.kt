package com.demo.smartchatbotapp.ui.viewmodel

import com.demo.smartchatbotapp.common.Constants.ERROR_NETWORK
import com.demo.smartchatbotapp.common.ErrorHandler
import com.demo.smartchatbotapp.common.NetworkMonitor
import com.demo.smartchatbotapp.domain.model.ChatMessage
import com.demo.smartchatbotapp.domain.repository.ChatRepository
import com.demo.smartchatbotapp.domain.usecase.GetChatHistoryUseCase
import com.demo.smartchatbotapp.domain.usecase.SendMessageUseCase
import com.demo.smartchatbotapp.util.TestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {
    private lateinit var viewModel: ChatViewModel
    private lateinit var repository: ChatRepository
    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var getChatHistoryUseCase: GetChatHistoryUseCase
    private lateinit var sendMessageUseCase: SendMessageUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        networkMonitor = mockk(relaxed = true)
        getChatHistoryUseCase = GetChatHistoryUseCase(repository)
        sendMessageUseCase = mockk(relaxed = true)
        viewModel = ChatViewModel(getChatHistoryUseCase, sendMessageUseCase, networkMonitor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        assertEquals(ChatState.Loading, viewModel.chatState.value)
    }

    @Test
    fun `loadChatHistory updates state with messages when online`() = runTest {
        // Given
        val messages = listOf(
            TestData.createChatMessage(id = 1L),
            TestData.createChatMessage(id = 2L, isUser = false)
        )
        every { networkMonitor.isOnline() } returns flowOf(true)
        every { getChatHistoryUseCase() } returns flowOf(messages)

        // When
        viewModel.loadChatHistory()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.chatState.value
        assertTrue(state is ChatState.Success)
        val successState = state as ChatState.Success
        assertEquals(2, successState.messages.size)
        assertEquals(1L, successState.messages[0].id)
        assertEquals(2L, successState.messages[1].id)
    }

    @Test
    fun `loadChatHistory shows error when offline`() = runTest {
        // Given
        every { networkMonitor.isOnline() } returns flowOf(false)
        every { getChatHistoryUseCase() } returns flowOf(emptyList())

        // When
        viewModel.loadChatHistory()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.chatState.value
        assertTrue(state is ChatState.Error)
        val errorState = state as ChatState.Error
        assertEquals(ERROR_NETWORK, errorState.message)
    }

    @Test
    fun `sendMessage updates state on success`() = runTest {
        // Given
        val message = "Test message"
        val botResponse = TestData.createChatMessage(id = 2L, content = "Test response", isUser = false)
        coEvery { sendMessageUseCase(message) } returns Result.success(botResponse)

        // When
        viewModel.sendMessage(message)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.chatState.value
        assertTrue(state is ChatState.Loading)
    }

    @Test
    fun `sendMessage shows error on failure`() = runTest {
        // Given
        val message = "Test message"
        val error = Exception("Test error")
        coEvery { sendMessageUseCase(message) } returns Result.failure(error)

        // When
        viewModel.sendMessage(message)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.chatState.value
        assertTrue(state is ChatState.Error)
        val errorState = state as ChatState.Error
        assertEquals(ErrorHandler.handleError(error), errorState.message)
    }
} 