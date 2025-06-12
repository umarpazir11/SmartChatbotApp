package com.demo.smartchatbotapp.data.remote

import com.demo.smartchatbotapp.data.remote.model.ChatRequest
import com.demo.smartchatbotapp.data.remote.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApiService {
    @POST("chat/completions")
    suspend fun sendMessage(@Body request: ChatRequest): ChatResponse
}

data class ChatRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Message>,
    val temperature: Double = 0.7
)

data class Message(
    val role: String,
    val content: String
)

data class ChatResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val message: Message,
    val finish_reason: String
) 