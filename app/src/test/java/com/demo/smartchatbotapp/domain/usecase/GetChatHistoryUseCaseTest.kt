package com.demo.smartchatbotapp.domain.usecase

import com.demo.smartchatbotapp.domain.model.ChatMessage
import com.demo.smartchatbotapp.domain.repository.ChatRepository
import com.demo.smartchatbotapp.util.TestData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetChatHistoryUseCaseTest {
    private lateinit var useCase: GetChatHistoryUseCase
    private lateinit var repository: ChatRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetChatHistoryUseCase(repository)
    }

    @Test
    fun `invoke returns chat history from repository`() = runTest {
        // Given
        val messages = listOf(
            TestData.createChatMessage(id = 1L),
            TestData.createChatMessage(id = 2L, isUser = false)
        )
        every { repository.getChatHistory() } returns flowOf(messages)

        // When
        val result = useCase().first()

        // Then
        assertEquals(2, result.size)
        assertEquals(1L, result[0].id)
        assertEquals(2L, result[1].id)
    }
} 