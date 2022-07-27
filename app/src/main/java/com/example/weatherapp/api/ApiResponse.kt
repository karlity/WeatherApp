package com.example.weatherapp.api

import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

/**
 * This file contains wrapper classes and functions to capture API responses and sorts them into wrappers
 * we can base UI functionality on
 */

// ApiResponse is a reusable wrapper for networks calls
// We can add to this overtime to adapt to API requirements and/or specific configurations etc.
sealed class ApiResponse<out TContent : Any> {
    sealed class Success<out TContent : Any>(
        open val httpCode: Int,
        open val httpHeaders: Headers
    ) : ApiResponse<TContent>() {
        data class WithContent<out TContent : Any>(
            val content: TContent,
            override val httpCode: Int = HttpStatus.HTTP_OK,
            override val httpHeaders: Headers = Headers.Builder().build()
        ) : Success<TContent>(httpCode, httpHeaders)

        data class NoContent<out TContent : Any>(
            override val httpCode: Int = HttpStatus.HTTP_NO_CONTENT,
            override val httpHeaders: Headers = Headers.Builder().build()
        ) : Success<TContent>(httpCode, httpHeaders)
    }

    sealed class Error : ApiResponse<Nothing>() {
        object NoInternet : Error()
        open class Response(
            val responseBody: ResponseBody?,
            val httpCode: Int,
            val httpHeaders: Headers = Headers.Builder().build()
        ) : Error() {
            override fun equals(other: Any?): Boolean {
                // basic equality, should only be used by unit tests
                return (other is Response) && other.httpCode == this.httpCode
            }
        }

        data class Unexpected(
            val throwable: Throwable?
        ) : Error()
    }
}

// toApiResponse sorts API responses into the respective ApiResponse wrapper that will be returned
suspend fun <T : Any> toApiResponse(block: suspend () -> Response<T>): ApiResponse<T> {
    return runCatching { block().wrapNetworkRelatedErrors() }.getOrElse { error ->
        Timber.e(error)
        if (error is IOException) return ApiResponse.Error.NoInternet
        return ApiResponse.Error.Unexpected(error)
    }
}

private fun <T : Any> Response<T>.wrapNetworkRelatedErrors(): ApiResponse<T> {
    val response = this
    return when {
        HttpStatus.isHttpSuccess(response.code()) -> {
            val body = response.body()
            if (body != null) {
                ApiResponse.Success.WithContent(
                    content = body,
                    httpCode = response.code(),
                    httpHeaders = response.headers()
                )
            } else {
                ApiResponse.Success.NoContent(
                    httpCode = response.code(),
                    httpHeaders = response.headers()
                )
            }
        }
        else -> {
            ApiResponse.Error.Response(
                responseBody = response.errorBody(),
                httpCode = response.code(),
                httpHeaders = response.headers()
            )
        }
    }
}