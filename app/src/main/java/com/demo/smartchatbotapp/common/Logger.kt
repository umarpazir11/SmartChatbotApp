package com.demo.smartchatbotapp.common

import android.util.Log
import com.demo.smartchatbotapp.common.Constants.LOG_TAG

/**
 * Utility class for logging throughout the application.
 * Provides consistent logging with different levels and optional tags.
 */
object Logger {
    private const val DEFAULT_TAG = LOG_TAG

    /**
     * Logs a debug message.
     *
     * @param message The message to log
     * @param tag Optional tag for the log message
     */
    fun d(message: String, tag: String = DEFAULT_TAG) {
        Log.d(tag, message)
    }

    /**
     * Logs an error message with optional throwable.
     *
     * @param message The error message
     * @param throwable Optional throwable to log
     * @param tag Optional tag for the log message
     */
    fun e(message: String, throwable: Throwable? = null, tag: String = DEFAULT_TAG) {
        if (throwable != null) {
            Log.e(tag, message, throwable)
        } else {
            Log.e(tag, message)
        }
    }

    /**
     * Logs an info message.
     *
     * @param message The message to log
     * @param tag Optional tag for the log message
     */
    fun i(message: String, tag: String = DEFAULT_TAG) {
        Log.i(tag, message)
    }

    /**
     * Logs a warning message.
     *
     * @param message The message to log
     * @param tag Optional tag for the log message
     */
    fun w(message: String, tag: String = DEFAULT_TAG) {
        Log.w(tag, message)
    }

    /**
     * Logs a verbose message.
     *
     * @param message The message to log
     * @param tag Optional tag for the log message
     */
    fun v(message: String, tag: String = DEFAULT_TAG) {
        Log.v(tag, message)
    }
} 