package com.demo.smartchatbotapp.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatRequest(
    @Json(name = "model") val model: String = "deepseek-chat",
    @Json(name = "messages") val messages: List<Message>,
    @Json(name = "temperature") val temperature: Double = 0.7,
    @Json(name = "max_tokens") val maxTokens: Int = 1000,
    @Json(name = "stream") val stream: Boolean = false
)

@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "role") val role: String,
    @Json(name = "content") val content: String
) 