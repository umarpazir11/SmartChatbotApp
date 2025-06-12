package com.demo.smartchatbotapp.common

import com.demo.smartchatbotapp.common.Constants.ERROR_API
import com.demo.smartchatbotapp.common.Constants.ERROR_DATABASE
import com.demo.smartchatbotapp.common.Constants.ERROR_NETWORK
import com.demo.smartchatbotapp.common.Constants.ERROR_UNKNOWN
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandlerTest {

    @Test
    fun `getErrorMessage returns network error for IOException`() {
        val error = IOException()
        assertEquals(ERROR_NETWORK, ErrorHandler.getErrorMessage(error))
    }

    @Test
    fun `getErrorMessage returns network error for UnknownHostException`() {
        val error = UnknownHostException()
        assertEquals(ERROR_NETWORK, ErrorHandler.getErrorMessage(error))
    }

    @Test
    fun `getErrorMessage returns network error for SocketTimeoutException`() {
        val error = SocketTimeoutException()
        assertEquals(ERROR_NETWORK, ErrorHandler.getErrorMessage(error))
    }

    @Test
    fun `getErrorMessage returns API error for HttpException`() {
        val error = HttpException(mockk(relaxed = true))
        assertEquals(ERROR_API, ErrorHandler.getErrorMessage(error))
    }

    @Test
    fun `getErrorMessage returns unknown error for generic Exception`() {
        val error = Exception()
        assertEquals(ERROR_UNKNOWN, ErrorHandler.getErrorMessage(error))
    }

    @Test
    fun `getErrorMessage returns custom message for Exception with message`() {
        val customMessage = "Custom error message"
        val error = Exception(customMessage)
        assertEquals(ERROR_UNKNOWN, ErrorHandler.getErrorMessage(error))
    }

    @Test
    fun `handleDatabaseError returns database error message`() {
        val error = Exception()
        assertEquals(ERROR_DATABASE, ErrorHandler.handleDatabaseError(error))
    }

    @Test
    fun `handleNetworkError returns network error message`() {
        val error = Exception()
        assertEquals(ERROR_NETWORK, ErrorHandler.handleNetworkError(error))
    }
} 