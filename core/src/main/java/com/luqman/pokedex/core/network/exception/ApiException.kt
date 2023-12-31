package com.luqman.pokedex.core.network.exception

import com.luqman.pokedex.core.model.UiText
import com.luqman.pokedex.core.R
import com.luqman.pokedex.core.network.exception.ApiException.NoConnectionError
import com.luqman.pokedex.core.network.exception.ApiException.TimeoutError
import java.io.IOException

/**
 * This sealed class is for storing the error type, so we can pass this to the
 * error callback, and provide a proper error message to the user, depending on the
 * error type.
 *
 * Due to Interceptor only pass through [IOException] derivatives,
 * this Exception will extend [IOException] instead of [Exception]
 *
 * e.g.
 * - [TimeoutError] will trigger timeout message
 * - [NoConnectionError] will trigger no connection message, etc.
 *
 */
sealed class ApiException : IOException() {

    abstract val titleMessage: UiText
    abstract val errorMessage: UiText

    object TimeoutError : ApiException() {
        override val errorMessage: UiText
            get() = UiText.StringResource(R.string.timeout_error_exception_title)
        override val titleMessage: UiText
            get() = UiText.StringResource(R.string.timeout_error_exception_message)
    }

    object NoConnectionError : ApiException() {
        override val errorMessage: UiText
            get() = UiText.StringResource(R.string.no_connection_error_exception_message)
        override val titleMessage: UiText
            get() = UiText.StringResource(R.string.no_connection_error_exception_title)
    }

    object JsonParsingException : ApiException() {
        override val errorMessage: UiText
            get() = UiText.StringResource(R.string.json_parsing_error_exception_message)
        override val titleMessage: UiText
            get() = UiText.StringResource(R.string.json_parsing_error_exception_title)
    }

    data class UnknownError(
        val throwable: Throwable,
        override val errorMessage: UiText = UiText.StringResource(R.string.unknown_error_exception_message),
        override val titleMessage: UiText = UiText.StringResource(R.string.unknown_error_exception_title)
    ) : ApiException()

    class HttpApiException(
        val code: Int,
        override val message: String,
    ) : ApiException() {

        override val errorMessage: UiText
            get() {
                return UiText.DynamicText(message)
            }

        override val titleMessage: UiText
            get() = UiText.StringResource(R.string.server_error_exception)

    }
}