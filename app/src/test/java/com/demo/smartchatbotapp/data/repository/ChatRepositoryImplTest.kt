package com.demo.smartchatbotapp.data.repository

import com.demo.smartchatbotapp.data.local.dao.ChatMessageDao
import com.demo.smartchatbotapp.data.remote.ChatApiService
import com.demo.smartchatbotapp.util.TestData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ChatRepositoryImplTest {
    private lateinit var repository: ChatRepositoryImpl
    private lateinit var dao: ChatMessageDao
    private lateinit var api: ChatApiService

    @Before
    fun setup() {
        dao = mockk()
        api = mockk()
        repository = ChatRepositoryImpl(dao, api)
    }

    @Test
    fun `getChatHistory returns mapped messages`() = runTest {
        // Given
        val entities = listOf(
            TestData.createChatMessageEntity(id = 1L),
            TestData.createChatMessageEntity(id = 2L, isUser = false)
        )
        every { dao.getAllMessages() } returns flowOf(entities)

        // When
        val result = repository.getChatHistory().first()

        // Then
        assertEquals(2, result.size)
        assertEquals(1L, result[0].id)
        assertTrue(result[0].isUser)
        assertEquals(2L, result[1].id)
        assertTrue(!result[1].isUser)
    }

    @Test
    fun `sendMessage saves user message and bot response`() = runTest {
        // Given
        val userMessage = "Test message"
        val botResponse = TestData.createChatResponse()
        coEvery { dao.insertMessage(any()) } returns 1L
        coEvery { api.sendMessage(any()) } returns botResponse

        // When
        val result = repository.sendMessage(userMessage)

        // Then
        assertTrue(result.isSuccess)
        assertEquals("Test response", result.getOrNull()?.content)
        assertTrue(!result.getOrNull()?.isUser!!)
        coVerify(exactly = 2) { dao.insertMessage(any()) }
    }

    @Test
    fun `sendMessage handles API error`() = runTest {
        // Given
        val userMessage = "Test message"
        val exception = Exception("API Error")
        coEvery { dao.insertMessage(any()) } returns 1L
        coEvery { api.sendMessage(any()) } throws exception

        // When
        val result = repository.sendMessage(userMessage)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { dao.insertMessage(any()) }
    }
} 