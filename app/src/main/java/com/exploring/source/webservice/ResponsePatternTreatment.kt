package com.exploring.source.webservice

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T : Any> parseSuccessResponse(response: HttpResponse): ResponseHttpPattern<T> {
    return try {
        val convertedResponseBody = response.body<T>()
        ResponseHttpPattern(
            statusCode = response.status.value.toString(),
            statusMessage = response.status.description,
            responseBody = convertedResponseBody
        )
    } catch (ex: Exception) {
        ResponseHttpPattern(
            success = false,
            statusCode = response.status.value.toString(),
            statusMessage = response.status.description,
            responseBody = null
        )
    }

}

suspend inline fun <reified T : Any> parseErrorResponse(response: HttpResponse): ResponseHttpPattern<T> {
    return try {
        /**
         * You can treat your API to standardize the error response, then you can try to convert the body automatically
         * */
        val responseError = response.body<ResponseHttpPattern<T>>()
        responseError.success = false
        responseError
    } catch (e: Exception) {
        ResponseHttpPattern(
            success = false,
            statusCode = response.status.value.toString(),
            statusMessage = response.status.description
        )
    }
}

inline fun  <reified T : Any> defaultResponseWithoutConnection(exception: Exception): ResponseHttpPattern<T> {
    return ResponseHttpPattern(
        success = false,
        statusCode = "0", // Without connection
        statusMessage = exception.message ?: "Without connection"
    )
}