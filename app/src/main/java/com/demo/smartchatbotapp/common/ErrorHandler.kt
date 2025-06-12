package com.demo.smartchatbotapp.common

import com.demo.smartchatbotapp.common.Constants.ERROR_API
import com.demo.smartchatbotapp.common.Constants.ERROR_DATABASE
import com.demo.smartchatbotapp.common.Constants.ERROR_NETWORK
import com.demo.smartchatbotapp.common.Constants.ERROR_UNKNOWN
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Utility class for handling different types of errors and converting them to user-friendly messages.
 */
object ErrorHandler {
    /**
     * Converts a throwable to a user-friendly error message.
     *
     * @param throwable The throwable to handle
     * @return A user-friendly error message
     */
    fun getErrorMessage(throwable: Throwable): String {
        return when (throwable) {
            is IOException, is UnknownHostException, is SocketTimeoutException -> ERROR_NETWORK
            is HttpException -> ERROR_API
            is Exception -> ERROR_UNKNOWN
            else -> throwable.message ?: ERROR_UNKNOWN
        }
    }

    /**
     * Logs the error and returns a user-friendly message.
     *
     * @param throwable The throwable to handle
     * @param tag Optional tag for logging
     * @return A user-friendly error message
     */
    fun handleError(throwable: Throwable, tag: String = Constants.LOG_TAG): String {
        Logger.e("Error occurred", throwable, tag)
        return getErrorMessage(throwable)
    }

    /**
     * Handles database errors specifically.
     *
     * @param throwable The throwable to handle
     * @return A user-friendly error message
     */
    fun handleDatabaseError(throwable: Throwable): String {
        Logger.e("Database error occurred", throwable)
        return ERROR_DATABASE
    }

    /**
     * Handles network errors specifically.
     *
     * @param throwable The throwable to handle
     * @return A user-friendly error message
     */
    fun handleNetworkError(throwable: Throwable): String {
        Logger.e("Network error occurred", throwable)
        return ERROR_NETWORK
    }
} 