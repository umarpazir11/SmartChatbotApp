package com.demo.smartchatbotapp.common

/**
 * Constants used throughout the application.
 */
object Constants {
    // API
    const val BASE_URL = "https://api.deepseek.com/v1/"
    const val API_KEY_HEADER = "Authorization"
    const val API_KEY_PREFIX = "Bearer "
    
    // Database
    const val DATABASE_NAME = "chat_database"
    const val CHAT_MESSAGES_TABLE = "chat_messages"
    
    // Network
    const val NETWORK_TIMEOUT_SECONDS = 30L
    const val MAX_RETRIES = 3
    
    // UI
    const val MAX_MESSAGE_LENGTH = 1000
    const val ANIMATION_DURATION = 300L
    
    // Error Messages
    const val ERROR_NETWORK = "Network error occurred. Please check your connection."
    const val ERROR_API = "Failed to communicate with the server."
    const val ERROR_DATABASE = "Failed to access local storage."
    const val ERROR_UNKNOWN = "An unexpected error occurred."
    
    // Logging
    const val LOG_TAG = "SmartChatbotApp"
} 